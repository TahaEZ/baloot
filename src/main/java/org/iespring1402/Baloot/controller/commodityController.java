package org.iespring1402.Baloot.controller;

import java.util.HashMap;
import java.util.Map;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.Commodity;
import org.iespring1402.Baloot.model.CommodityByIdView;
import org.iespring1402.Baloot.model.Provider;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/commodity")
@CrossOrigin
public class commodityController {
    private Baloot balootInstance = new Baloot();

    @GetMapping("/")
    public @ResponseBody Object list() {
        Map commoditiesList = new HashMap();
        commoditiesList.put("commoditiesList", balootInstance.getCommodities());
        return commoditiesList;
    }

    @GetMapping(value = "")
    public Object getMethodName(@RequestParam int id) {
        Commodity commodity = balootInstance.findCommodityById(id);
        CommodityByIdView result;
        Provider provider = balootInstance.findProviderByProviderId(commodity.getProviderId());
        result = new CommodityByIdView(commodity.getId(), commodity.getName(), provider.getName(),
                commodity.getPrice(), commodity.getCategories(), commodity.getRating());
        return result;
    }

}
