package org.iespring1402.Baloot.repositories;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.iespring1402.Baloot.models.DiscountCode;

import org.springframework.boot.ApplicationArguments;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

@Component
public class InitDatabase implements ApplicationRunner {
    static final String API_URL = "http://5.253.25.110:5000";

    @Autowired
    private DiscountDAO discountDao;


    private void getDiscounts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<DiscountCode>discountCodes = new ArrayList<>();
        String discountCodesJSON = fetchData("/api/discount");
        discountCodes = new ArrayList<>(Arrays.asList(mapper.readValue(discountCodesJSON, DiscountCode[].class)));

        for (int i = 0; i < discountCodes.size(); i++) {
            this.discountDao.save(discountCodes.get(i));
        }
    }
    
    private String fetchData(String path) {
        try {
            URL url = new URL(API_URL + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

 
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Initializing database ... ");
        System.out.println("Getting Discounts... ");
        getDiscounts();
    }
}
