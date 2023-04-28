package org.iespring1402.Baloot.controller;

import java.util.HashMap;
import java.util.Map;

import org.iespring1402.Baloot.model.Baloot;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
    