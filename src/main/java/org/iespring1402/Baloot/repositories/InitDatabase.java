package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.DiscountCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.springframework.boot.ApplicationArguments;

@Component
public class InitDatabase implements ApplicationRunner {
    static String baseUrl = "http://5.253.25.110:5000/api";

    @Autowired
    private DiscountCode discountDao;


    private void getDiscounts() throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/discount").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for (int i = 0; i < jasonInput.size(); i++) {
            DiscountCode newDiscount = parseDiscount(jasonInput.get(i).toString());
            this.discountDao.save(newDiscount);
        }
    }

    static public DiscountCode parseDiscount(String jsonString) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addDiscountJsonVariables, j);
        String discountCode = (String) j.get(addDiscountJsonVariables[0]);
        long discount = (long) j.get(addDiscountJsonVariables[1]);

        return new Discount(discountCode, discount);

    }
    
}
