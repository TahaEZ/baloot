package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.CategoryFilter;
import org.iespring1402.Baloot.model.Commodity;
import org.iespring1402.Baloot.model.CommodityNoInStock;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/commodities")
@CrossOrigin
public class commoditiesController {
    private Baloot balootInstance = new Baloot();

    @GetMapping("/")
    public @ResponseBody Object list() {
        Map commoditiesList = new HashMap();
        commoditiesList.put("commoditiesList", balootInstance.getCommodities());
        return commoditiesList;
    }

    @GetMapping(value = "")
    public Object getMethodName(@RequestParam String category) {
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

    
}
