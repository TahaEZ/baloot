package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.CategoryFilter;
import org.iespring1402.Baloot.entities.Commodity;
import org.iespring1402.Baloot.entities.DiscountCode;
import org.iespring1402.Baloot.entities.Provider;
import org.iespring1402.Baloot.entities.User;
import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.iespring1402.Baloot.repositories.CommentDAO;
import org.iespring1402.Baloot.repositories.CommodityDAO;
import org.iespring1402.Baloot.repositories.CommodityRepository;
import org.iespring1402.Baloot.repositories.DiscountRepository;
import org.iespring1402.Baloot.repositories.ProviderDAO;
import org.iespring1402.Baloot.repositories.UserDAO;
import org.iespring1402.Baloot.response.FailedResponse;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/v1/commodities")
@CrossOrigin
public class CommoditiesController {
    @Autowired
    private CommodityDAO commodityDao;

    @Autowired
    private ProviderDAO providerDAO;

    @Autowired
    private UserDAO userDAO;


    @GetMapping("")
    public @ResponseBody Object list(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12") Integer pageSize,
            @RequestParam(value = "available", defaultValue = "false") Boolean availableCommodities,
            @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }
        HashMap<String, Object> returnResponse = new HashMap<>();
        List<Commodity> allCommodities = new ArrayList<>();
        if (availableCommodities == true) {
            allCommodities = commodityDao.getAvailableCommodities();
        } else {
            allCommodities = commodityDao.getAllCommodities();
        }

        PaginationController paginator = new PaginationController((ArrayList<Commodity>)allCommodities);
        returnResponse = paginator.paginateItems(pageSize, pageNo);
        if (returnResponse.size() == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Page Number.");
        } else {
            return returnResponse;
        }

    }

   

    @GetMapping(value = "", params = { "searchType", "searchVal" })
    public Object getCommoditiesByCategory(
            @PathParam("searchType") String searchType,
            @PathParam("searchVal") String searchVal,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12") Integer pageSize,
            @RequestParam(value = "available", defaultValue = "false") Boolean availableCommodities,
            @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }
        List<Commodity> result = new ArrayList<>();
        HashMap<String, Object> filteredCommoditiesList = new HashMap<>();
        
        if (searchVal.equals("category") || searchVal.equals("name") || searchVal.equals("provider")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid search.");
        } else {

            if (searchType.equals("category")) {
                System.out.println(searchVal);
                result = commodityDao.findByCategory(searchVal,availableCommodities);
                // filteredWithStock = filter.applyFilter(allCommodities);
            } else if (searchType.equals("name")) {

                result = commodityDao.findCommodityByName(searchVal , availableCommodities);

            } else if (searchType.equals("provider")) {
                result = commodityDao.getCommoditiesByProviderName(searchVal , availableCommodities);
            }
            // if (filteredWithStock.isEmpty()) {
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Didn't find any
            // Commodities.");
            // }
        }
        PaginationController paginator = new PaginationController((ArrayList<Commodity>) result);
        filteredCommoditiesList = paginator.paginateItems(pageSize, pageNo);
        if (filteredCommoditiesList.size() == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Page Number.");
        } else {
            return filteredCommoditiesList;
        }

    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Object getCommodityById(@PathVariable("id") int id, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        Commodity commodity = commodityDao.findCommodityById(id);
        if (commodity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Commodity Not Found.");
        }
        CommodityDTO result;
        Provider provider = providerDAO.findById(commodity.getProviderId());
        ArrayList<Commodity> suggestedCommodities = getSuggestedCommodities(commodity.getId());
        System.out.println(suggestedCommodities.size());
        result = new CommodityDTO(commodity.getId(), commodity.getName(), provider.getId(), provider.getName(),
                commodity.getPrice(), commodity.getCategories(), commodity.getRating(), commodity.getInStock(),
                suggestedCommodities, commodity.getRatings().keySet().size(), commodity.getImage());
        return result;
    }

    public ArrayList<Commodity> getSuggestedCommodities(int commodityId) {
        ArrayList<Commodity> suggestedCommodities = new ArrayList<>(commodityDao.getAllCommodities());
        Commodity commodity = commodityDao.findCommodityById(commodityId);
        if (commodity == null) {
            return null;
        } else {
            Comparator<Commodity> comparator = (commodity1, commodity2) -> {
                List<String> currentCategories = commodity.getCategories();
                int is_in_similar_category1 = commodity1.getCategories().containsAll(currentCategories) ? 1 : 0;
                int is_in_similar_category2 = commodity2.getCategories().containsAll(currentCategories) ? 1 : 0;
                float score1 = is_in_similar_category1 * 11 + commodity1.getRating();
                float score2 = is_in_similar_category2 * 11 + commodity2.getRating();
                return Float.compare(score2, score1);
            };
            suggestedCommodities.sort(comparator);
            for (int i = 0; i < suggestedCommodities.size(); i++) {
                if (suggestedCommodities.get(i).getId() == commodity.getId()) {
                    suggestedCommodities.remove(i);
                    break;
                }
            }
            suggestedCommodities = new ArrayList<>(suggestedCommodities.subList(0, 4));
            return suggestedCommodities;
        }

    }

    @GetMapping(value = "", params = "providerId")
    @ResponseBody
    public Object getCommodityByProviderId(@PathParam("providerId") int providerId,
            @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        List<Commodity> commodities = commodityDao.getCommodityByProviderId(providerId);
        if (commodities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This provider didn't provide any commodity.");
        }
        return commodities;
    }

    @PostMapping(value = "")
    @ResponseBody
    public Object addCommodity(@RequestBody Commodity commodity, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        if (commodityDao.findCommodityById(commodity.getId()).getId() == commodity.getId()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate was occurred!");
        } else {
            commodityDao.save(commodity);
            return commodity;
        }
    }

    @PostMapping(value = "/{id}/ratings")
    @ResponseBody
    public Object rateCommodity(@PathVariable("id") int id, @RequestParam String username, int rate,
            @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        if (rate >= 1 && rate <= 10) {
            try {
                if (userDAO.getUserByUsername(username) == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found!");
                }
                rateCommodity(username, id, rate);

            } catch (Exception e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    public Response rateCommodity(String username, int commodityId, int score) {
        Commodity commodity = commodityDao.findCommodityById(commodityId);
        if (commodity != null) {
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                int providerId = commodity.getProviderId();
                Provider provider = providerDAO.findById(providerId);
                if (provider != null) {
                    if (score >= 1 && score <= 10) {
                        commodity.addRating(username, score);

                        float commodityRating = commodity.getRating();
                        provider.addRating(commodityId, commodityRating);
                        commodityDao.save(commodity);

                        return new SuccessfulResponse();
                    } else {
                        return new FailedResponse("Score must be an integer from 1 to 10!");
                    }
                } else
                    return new FailedResponse("No provider found for this commodity!");
            } else {
                return new FailedResponse("No user found with this username!");
            }
        } else
            return new FailedResponse("No commodity found with this commodity id!");
    }

}
