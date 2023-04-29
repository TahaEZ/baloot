package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Baloot balootInstance = new Baloot();

    @GetMapping("")
    public @ResponseBody Object list() {
        Map commoditiesList = new HashMap();
        commoditiesList.put("commoditiesList", balootInstance.getCommodities());
        return commoditiesList;
    }

    @GetMapping(value = "" , params = "category")
    public Object getCommoditiesByCategory(@PathParam("category") String category) {
        CategoryFilter filter = new CategoryFilter(category);
        Map filteredCommoditiesList = new HashMap();
        ArrayList<Commodity> filteredWithStock = filter.applyFilter(balootInstance.getCommodities());
        ArrayList<CommodityNoInStock> filteredWithoutInStock = new ArrayList<CommodityNoInStock>();
        for (Commodity commodity : filteredWithStock) {
            CommodityNoInStock oneCommodityWithNoInStock = new CommodityNoInStock(commodity.getId(),
                    commodity.getName(),
                    commodity.getProviderId(), commodity.getPrice(), commodity.getCategories(), commodity.getRating());
            filteredWithoutInStock.add(oneCommodityWithNoInStock);
        }
        filteredCommoditiesList.put("commoditiesListByCategory", filteredWithoutInStock);
        return filteredCommoditiesList;
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

    @PostMapping(value = "")
    @ResponseBody
    public Object addCommodity(@RequestBody Commodity commodity) {

        if (balootInstance.findCommodityById(commodity.getId()).getId() == commodity.getId()) {
          return  ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate was occurred!");
        }

        else {
            balootInstance.addCommodity(commodity);
            return commodity;
        }

    }

    @PostMapping(value = "/{id}/ratings")
    @ResponseBody
    public Object rateCommodity(@PathVariable("id") int id,String username, int rate) {

        if (rate >= 1 && rate <= 10) {
            try {
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
