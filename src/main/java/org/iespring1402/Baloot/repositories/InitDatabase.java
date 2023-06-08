package org.iespring1402.Baloot.repositories;

import java.util.ArrayList;

import org.iespring1402.Baloot.entities.Comment;
import org.iespring1402.Baloot.entities.Commodity;
import org.iespring1402.Baloot.entities.DiscountCode;
import org.iespring1402.Baloot.entities.Provider;
import org.iespring1402.Baloot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Autowired
    private CommodityDAO commodityDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ProviderDAO providerDAO;
    
    @Autowired
    private UserDAO userDAO;

    private void getDiscounts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<DiscountCode> discountCodes = new ArrayList<>();
        String discountCodesJSON = fetchData("/api/discount");
        discountCodes = new ArrayList<>(Arrays.asList(mapper.readValue(discountCodesJSON, DiscountCode[].class)));

        for (int i = 0; i < discountCodes.size(); i++) {
            this.discountDao.save(discountCodes.get(i));
        }
    }

    private void getCommodities() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Commodity> commodities = new ArrayList<>();
        String commoditiesJSON = fetchData("/api/v2/commodities");
        commodities = new ArrayList<>(Arrays.asList(mapper.readValue(commoditiesJSON, Commodity[].class)));

        for (int i = 0; i < commodities.size(); i++) {
            this.commodityDAO.save(commodities.get(i));
        }
    }

    private void getComments() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Comment> comments = new ArrayList<>();
        String commentsJSON = fetchData("/api/comments");
        comments = new ArrayList<>(Arrays.asList(mapper.readValue(commentsJSON, Comment[].class)));

        for (Comment comment : comments) {
            this.commentDAO.save(comment);
        }
    }

    private void getProviders() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Provider> providers = new ArrayList<>();
        String providersJson = fetchData("/api/v2/providers");
        providers = new ArrayList<>(Arrays.asList(mapper.readValue(providersJson, Provider[].class)));

        for (Provider provider : providers) {
            this.providerDAO.save(provider);
        }
    }

    private void getUsers() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> users = new ArrayList<>();
        String userJSON = fetchData("/api/users");
        users = new ArrayList<>(Arrays.asList(mapper.readValue(userJSON, User[].class)));

        for (User user : users) {
            this.userDAO.save(user);
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
        System.out.println("Getting Commodities... ");
        getCommodities();
        // comments in the external API are empty so we don't initialize comments in the
        // database
        // System.out.println("Getting Comments... ");
        // getComments();
        System.out.println("Getting Providers... ");
        getProviders();
        System.out.println("Getting Users... ");
        getUsers();
        System.out.println("Initialization completed. ");
    }
}
