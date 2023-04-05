package org.iespring1402;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;
import org.iespring1402.views.CommodityNoInStock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;

public class Baloot {
    private static final String API_URL = "http://5.253.25.110:5000";
    private static Baloot instance;
    private String currentUser;
    private ArrayList<User> users;
    private ArrayList<Commodity> commodities;
    private ArrayList<Provider> providers;
    private ArrayList<Comment> comments;
    private  ArrayList<DiscountCode> discountCodes;

    public Baloot() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String commentsJson = fetchData("/api/comments");
            comments = new ArrayList<>(Arrays.asList(mapper.readValue(commentsJson, Comment[].class)));
            String usersJson = fetchData("/api/users");
            users = new ArrayList<>(Arrays.asList(mapper.readValue(usersJson, User[].class)));
            String providersJson = fetchData("/api/providers");
            providers = new ArrayList<>(Arrays.asList(mapper.readValue(providersJson, Provider[].class)));
            String commoditiesJson = fetchData("/api/commodities");
            ArrayList<Commodity> commodityArrayList = new ArrayList<>(Arrays.asList(mapper.readValue(commoditiesJson, Commodity[].class)));
            commodities = new ArrayList<>();
            for (Commodity commodity : commodityArrayList) {
                addCommodity(commodity);
            }
            String discountCodesJSON = fetchData("/api/discount");
            ArrayList<DiscountCode> discountsArrayList = new ArrayList<>(Arrays.asList(mapper.readValue(discountCodesJSON, DiscountCode[].class)));
            discountCodes = new ArrayList<>();
            for (DiscountCode discountCode : discountsArrayList) {
                addDiscountCode(discountCode);
            }
            currentUser = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Baloot getInstance() {
        if (instance == null)
            instance = new Baloot();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
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

    public boolean discountCodeValidityCheck(String discountCode)
    {
        for(DiscountCode discountCodeTemp : discountCodes)
        {
            if(discountCodeTemp.getCode() == discountCode && !discountCodeTemp.isDeprecated())
            {
                return true;
            }
        }
        return false;
    }

    public Response deprecateDiscountCode(String discountCode)
    {
        for(DiscountCode discountCodeTemp : discountCodes)
        {
            if(discountCodeTemp.getCode() == discountCode)
            {
                discountCodeTemp.setDeprecated(true);
                return new SuccessfulResponse("Discount code deprecated.");
            }
        }
        return  new FailedResponse("Discount code not found to deprecate.");
    }

    public Response insertDiscountCodeToUsedForUser(DiscountCode discountCode,String username)
    {
        try {
            User  user = findUserByUsername(username);
            return   user.addToUsedDiscounts(discountCode);
        }
        catch (Exception e)
        {
            return new FailedResponse("username not found!");
        }
    }

    public boolean addUser(User newUser) {
        String username = newUser.username;

        if (!isUsernameValid(username))
            return false;

        User toBeUpdatedUser = findUserByUsername(username);

        if (toBeUpdatedUser != null) {
            String password = newUser.password;
            String email = newUser.email;
            String birthDate = newUser.birthDate;
            String address = newUser.address;
            long credit = newUser.credit;

            toBeUpdatedUser.updateUser(password, email, birthDate, address, credit);
        } else
            users.add(newUser);

        return true;
    }

    private boolean isUsernameValid(String username) {
        return !username.matches(".*[@!#$%^&*()\\u0020\\u200C].*"); // false if username contains any special character.
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public boolean addCommodity(Commodity commodity) {
        int providerId = commodity.getProviderId();
        Provider provider = findProviderByProviderId(providerId);
        if (provider == null) return false;
        provider.addRating(commodity.getId(), commodity.getRating());
        commodities.add(commodity);
        return true;
    }
    public void addDiscountCode(DiscountCode discountCode) {
        discountCodes.add(discountCode);
    }

    public Commodity findCommodityById(int commodityId) {
        for (Commodity commodity : commodities) {
            if (commodity.getId() == commodityId) {
                return commodity;
            }
        }
        return null;
    }

    public void updateCommodity(int commodityId, Commodity update) {
        for (int i = 0; i < commodities.size(); i++) {
            if (commodities.get(i).getId() == commodityId) {
                commodities.set(i, update);
            }
        }
    }

    public boolean commodityExist(int id) {
        if (commodities.isEmpty()) {
            return false;
        }
        ListIterator<Commodity> it = commodities.listIterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Response addToBuyList(String username, int commodityId) {
        User user = findUserByUsername(username);
        if (user == null)
            return new FailedResponse("No user found with this username!");
        else {
            Commodity commodity = findCommodityById(commodityId);
            if (commodity == null) {
                return new FailedResponse("No commodity found with this commodity id!");
            } else if (!commodity.isInStock()) {
                return new FailedResponse("This commodity is out of stock!");
            } else
                return user.addToBuyList(commodityId);
        }
    }

    public Response removeFromBuyList(String username, int commodityId) {
        User user = findUserByUsername(username);
        if (user == null)
            return new FailedResponse("No user found with this username!");
        else
            return user.removeFromBuyList(commodityId);
    }

    public ArrayList<CommodityNoInStock> getBuyList(String username) {
        User user = findUserByUsername(username);
        if (user != null) {
            ArrayList<Integer> BuyList = user.getBuyList().getList();
            ArrayList<CommodityNoInStock> result = new ArrayList<CommodityNoInStock>();
            for (int commodityId : BuyList) {
                Commodity commodity = findCommodityById(commodityId);

                CommodityNoInStock commodityWithNoInStockField = new CommodityNoInStock(commodity.getId(), commodity.getName(),
                        commodity.getProviderId(), commodity.getPrice(), commodity.getCategories(), commodity.getRating());

                result.add(commodityWithNoInStockField);
            }
            return result;
        } else
            return null;
    }

    public Response rateCommodity(String username, int commodityId, int score) {
        Commodity commodity = findCommodityById(commodityId);
        if (commodity != null) {
            User user = findUserByUsername(username);
            if (user != null) {
                int providerId = commodity.getProviderId();
                Provider provider = findProviderByProviderId(providerId);
                if (provider != null) {
                    if (score >= 1 && score <= 10) {
                        commodity.addRating(username, score);

                        float commodityRating = commodity.getRating();
                        provider.addRating(commodityId, commodityRating);

                        return new SuccessfulResponse();
                    } else {
                        return new FailedResponse("Score must be an integer from 1 to 10!");
                    }
                } else
                    return new FailedResponse("No provider found for this commodity!");
            } else {
                return new FailedResponse("No user found with this username!");
            }
        } else
            return new FailedResponse("No commodity found with this commodity id!");
    }

    public Provider findProviderByProviderId(int providerId) {
        for (Provider provider : providers) {
            if (provider.getId() == providerId) {
                return provider;
            }
        }
        return null;
    }

    public Comment findCommentById(String commentId) {
        for (Comment comment : comments) {
            if (comment.getId().equals(commentId)) {
                return comment;
            }
        }
        return null;
    }

    public ArrayList<Comment> getFilteredCommentsByCommodityId(int commodityId) {
        ArrayList<Comment> filteredComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getCommodityId() == commodityId) {
                filteredComments.add(comment);
            }
        }
        return filteredComments;
    }

    public void addProvider(Provider newProvider) {
        int providerId = newProvider.getId();
        Provider toBeUpdatedProvider = findProviderByProviderId(providerId);

        if (toBeUpdatedProvider != null) {
            String name = newProvider.getName();
            String registryDate = newProvider.getRegistryDate();
            HashMap<Integer, Float> ratings = newProvider.getRatings();

            toBeUpdatedProvider.updateProvider(name, registryDate, ratings);
        } else
            providers.add(newProvider);
    }

    public Response addComment(String username, int commodityId, String text) {
        User user = findUserByUsername(username);
        if (user == null)
            return new FailedResponse("No user found with this username!");
        Commodity commodity = findCommodityById(commodityId);
        if (commodity == null) {
            return new FailedResponse("No commodity found with this commodity id!");
        }
        if (text.isEmpty()) {
            return new FailedResponse("Your comment can't be empty!");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        Comment comment = new Comment(user.getEmail(), commodityId, text, dateFormat.format(date));
        comments.add(comment);
        return new SuccessfulResponse();
    }

    public Response voteComment(String username, String commentId, int vote) {
        User user = findUserByUsername(username);
        if (user == null)
            return new FailedResponse("No user found with this username!");
        Comment comment = Baloot.getInstance().findCommentById(commentId);
        if (comment == null) {
            return new FailedResponse("No comment found with this comment id!");
        }
        if (vote == 1 || vote == -1 || vote == 0) {
            comment.voteComment(username, vote);
            return new SuccessfulResponse();
        } else {
            return new FailedResponse("Vote must be 1, -1 or 0 (like, dislike or neutral)");
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
