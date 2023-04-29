package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.Commodity;
import org.iespring1402.Baloot.model.CommodityByIdView;
import org.iespring1402.Baloot.model.Provider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/commodity")
@CrossOrigin
public class commodityController {
    private Baloot balootInstance = new Baloot();

    @GetMapping(value = "")
    @ResponseBody
    public Object getCommodityById(@RequestParam int id) {
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

    @PostMapping(value = "/rate")
    public Object rateCommodity(@RequestParam String username, int id, int rate) {

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
