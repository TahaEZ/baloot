package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.CategoryFilter;
import org.iespring1402.Baloot.model.Commodity;
import org.iespring1402.Baloot.model.CommodityByIdView;
import org.iespring1402.Baloot.model.CommodityNoInStock;
import org.iespring1402.Baloot.model.Provider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/commodities")
@CrossOrigin
public class commoditiesController {
    private Baloot balootInstance = Baloot.getInstance();

    @GetMapping("")
    public @ResponseBody Object list(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12") Integer pageSize) {
        HashMap<String, Object> returnResponse = new HashMap<>();
        int commoditySize = balootInstance.getCommodities().size();
        int totalPagesNumber = commoditySize / pageSize;
        if (commoditySize % pageSize != 0) {
            totalPagesNumber += 1;
        }
        returnResponse.put("totalPages", totalPagesNumber);
        if (pageNo * pageSize > commoditySize) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Page not available!");
        } else {
            int start = (pageNo - 1) * pageSize;
            ArrayList<Commodity> commodities = new ArrayList<>();
            if ((commoditySize - (pageNo * pageSize)) >= pageSize) {

                for (int dynamicStart = start; dynamicStart < start + pageSize; dynamicStart++) {
                    commodities.add(balootInstance.getCommodities().get(dynamicStart));
                }
                returnResponse.put("commoditiesList", commodities);

                return returnResponse;
            } else {
                for (int dynamicStart = (pageNo * pageSize) - 1; dynamicStart < commoditySize; dynamicStart++) {
                    commodities.add(balootInstance.getCommodities().get(dynamicStart));
                }
                returnResponse.put("commoditiesList", commodities);

                return returnResponse;
            }
        }

    }

    private ArrayList<CommodityNoInStock> deleteInStock(ArrayList<Commodity> commodities) {
        ArrayList<CommodityNoInStock> filteredWithoutInStock = new ArrayList<CommodityNoInStock>();
        for (Commodity commodity : commodities) {
            CommodityNoInStock oneCommodityWithNoInStock = new CommodityNoInStock(commodity.getId(),
                    commodity.getName(),
                    commodity.getProviderId(), commodity.getPrice(), commodity.getCategories(), commodity.getRating());
            filteredWithoutInStock.add(oneCommodityWithNoInStock);
        }
        return filteredWithoutInStock;

    }

    private ArrayList<Commodity> listAvailableCommodities(ArrayList<Commodity> commodities) {
        ArrayList<Commodity> availableCommodities = new ArrayList<>();
        for (Commodity commodity : balootInstance.getCommodities()) {
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
            @RequestParam(value = "available", defaultValue = "false") Boolean availableCommodities) {
        if (searchVal.equals("category") || searchVal.equals("name") || searchVal.equals("provider")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid search.");
        } else {

            Map filteredCommoditiesList = new HashMap();
            ArrayList<Commodity> filteredWithStock = new ArrayList<>();
            if (searchType.equals("category")) {
                CategoryFilter filter = new CategoryFilter(searchVal);
                System.out.println(searchVal);
                filteredWithStock = filter.applyFilter(balootInstance.getCommodities());
            } else if (searchType.equals("name")) {

                for (Commodity commodity : balootInstance.getCommodities()) {
                    if (commodity.getName().toLowerCase().contains(searchVal.toLowerCase())) {
                        filteredWithStock.add(commodity);
                    }
                }
            } else if (searchType.equals("provider")) {
                ArrayList<Provider> foundProviders = balootInstance.searchProviderByName(searchVal);
                for (Provider provider : foundProviders) {
                    for (Commodity commodity : balootInstance.getCommodities()) {
                        if (commodity.getProviderId() == provider.getId()) {
                            filteredWithStock.add(commodity);
                        }
                    }
                }
            }
            if (filteredWithStock.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Didn't find any Commodities.");
            }
            if (availableCommodities) {
                filteredCommoditiesList.put("searchedCommodities",
                        deleteInStock(listAvailableCommodities(filteredWithStock)));
                return filteredCommoditiesList;
            } else {
                filteredCommoditiesList.put("searchedCommodities",
                        deleteInStock(filteredWithStock));
                return filteredCommoditiesList;
            }

        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Object getCommodityById(@PathVariable("id") int id) {
        Commodity commodity = balootInstance.findCommodityById(id);
        if (commodity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Commodity Not Found.");
        }
        CommodityByIdView result;
        Provider provider = balootInstance.findProviderByProviderId(commodity.getProviderId());
        result = new CommodityByIdView(commodity.getId(), commodity.getName(), provider.getName(),
                commodity.getPrice(), commodity.getCategories(), commodity.getRating());
        return result;
    }

    @GetMapping(value = "", params = "providerId")
    @ResponseBody
    public Object getCommodityByProviderId(@PathParam("providerId") int providerId) {
        ArrayList<Commodity> commodities = balootInstance.findCommoditiesByProviderId(providerId);
        if (commodities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This provider didn't provide any commodity.");
        }

        return commodities;
    }

    @PostMapping(value = "")
    @ResponseBody
    public Object addCommodity(@RequestBody Commodity commodity) {

        if (balootInstance.findCommodityById(commodity.getId()).getId() == commodity.getId()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate was occurred!");
        }

        else {
            balootInstance.addCommodity(commodity);
            return commodity;
        }

    }

    @PostMapping(value = "/{id}/ratings")
    @ResponseBody
    public Object rateCommodity(@PathVariable("id") int id, @RequestParam String username, int rate) {

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
        }

        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

}
