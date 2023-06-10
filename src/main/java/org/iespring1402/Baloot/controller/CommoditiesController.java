package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.CategoryFilter;
import org.iespring1402.Baloot.entities.Commodity;
import org.iespring1402.Baloot.entities.DiscountCode;
import org.iespring1402.Baloot.models.Provider;
import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.iespring1402.Baloot.repositories.CommentDAO;
import org.iespring1402.Baloot.repositories.CommodityDAO;
import org.iespring1402.Baloot.repositories.CommodityRepository;
import org.iespring1402.Baloot.repositories.DiscountRepository;
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
    private CommodityRepository commodityRepo;

    private Baloot balootInstance = Baloot.getInstance();

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
        ArrayList<Commodity> allCommodities = new ArrayList<>();
        if (availableCommodities == true) {
            allCommodities = listAvailableCommodities(balootInstance.getCommodities());
        } else {
            allCommodities = balootInstance.getCommodities();
        }

        PaginationController paginator = new PaginationController(allCommodities);
        returnResponse = paginator.paginateItems(pageSize, pageNo);
        if (returnResponse.size() == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Page Number.");
        } else {
            return returnResponse;
        }

    }

    private ArrayList<Commodity> listAvailableCommodities(ArrayList<Commodity> commodities) {
        ArrayList<Commodity> availableCommodities = new ArrayList<>();
        for (Commodity commodity : commodities) {
            if (commodity.isInStock()) {
                availableCommodities.add(commodity);
            }
        }
        return availableCommodities;
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
        ArrayList<Commodity> allCommodities = new ArrayList<>();
        List<Commodity> result = new ArrayList<>();
        HashMap<String, Object> filteredCommoditiesList = new HashMap<>();
        ArrayList<Commodity> filteredWithStock = new ArrayList<>();
        if (availableCommodities == true) {
            allCommodities = listAvailableCommodities(balootInstance.getCommodities());
        } else {
            allCommodities = balootInstance.getCommodities();
        }
        if (searchVal.equals("category") || searchVal.equals("name") || searchVal.equals("provider")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid search.");
        } else {

            if (searchType.equals("category")) {
                CategoryFilter filter = new CategoryFilter(searchVal);
                System.out.println(searchVal);
                result = commodityDao.findByCategory(searchVal);
                // filteredWithStock = filter.applyFilter(allCommodities);
            } else if (searchType.equals("name")) {

                for (Commodity commodity : allCommodities) {
                    if (commodity.getName().toLowerCase().contains(searchVal.toLowerCase())) {
                        filteredWithStock.add(commodity);
                    }
                }
            } else if (searchType.equals("provider")) {
                ArrayList<Provider> foundProviders = balootInstance.searchProviderByName(searchVal);
                for (Provider provider : foundProviders) {
                    for (Commodity commodity : allCommodities) {
                        if (commodity.getProviderId() == provider.getId()) {
                            filteredWithStock.add(commodity);
                        }
                    }
                }
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

        Commodity commodity = balootInstance.findCommodityById(id);
        if (commodity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Commodity Not Found.");
        }
        CommodityDTO result;
        Provider provider = balootInstance.findProviderByProviderId(commodity.getProviderId());
        ArrayList<Commodity> suggestedCommodities = balootInstance.getSuggestedCommodities(commodity.getId());
        System.out.println(suggestedCommodities.size());
        result = new CommodityDTO(commodity.getId(), commodity.getName(), provider.getId(), provider.getName(),
                commodity.getPrice(), commodity.getCategories(), commodity.getRating(), commodity.getInStock(),
                suggestedCommodities, commodity.getRatings().keySet().size(), commodity.getImage());
        return result;
    }

    @GetMapping(value = "", params = "providerId")
    @ResponseBody
    public Object getCommodityByProviderId(@PathParam("providerId") int providerId,
            @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        ArrayList<Commodity> commodities = balootInstance.findCommoditiesByProviderId(providerId);
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

        if (balootInstance.findCommodityById(commodity.getId()).getId() == commodity.getId()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate was occurred!");
        } else {
            balootInstance.addCommodity(commodity);
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
                if (balootInstance.findUserByUsername(username) == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found!");
                }
                balootInstance.rateCommodity(username, id, rate);

            } catch (Exception e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

}
