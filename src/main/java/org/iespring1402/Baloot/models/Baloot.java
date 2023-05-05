package org.iespring1402.Baloot.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.iespring1402.Baloot.models.views.CommodityNoInStock;
import org.iespring1402.Baloot.response.FailedResponse;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;

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
    private ArrayList<DiscountCode> discountCodes;

    public Baloot() {
        ObjectMapper mapper = new ObjectMapper();
        try {
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
            comments = new ArrayList<>();
            discountCodes = new ArrayList<>();
            String discountCodesJSON = fetchData("/api/discount");
            discountCodes = new ArrayList<>(Arrays.asList(mapper.readValue(discountCodesJSON, DiscountCode[].class)));
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

    public DiscountCode findDiscountCodeByCode(String code) {
        for (DiscountCode discountCode : discountCodes) {
            if (discountCode.getCode().equals(code)) {
                return discountCode;
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

    public boolean discountCodeValidityCheck(String discountCode) {
        for (DiscountCode discountCodeTemp : discountCodes) {
            if (discountCodeTemp.getCode().equals(discountCode) && discountCodeTemp.isDeprecated() == false) {
                return true;
            }
        }
        return false;
    }

    public Response deprecateDiscountCode(String discountCode) {
        for (DiscountCode discountCodeTemp : discountCodes) {
            if (discountCodeTemp.getCode() == discountCode) {
                discountCodeTemp.setDeprecated(true);
                return new SuccessfulResponse("Discount code deprecated.");
            }
        }
        return new FailedResponse("Discount code not found to deprecate.");
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

    public ArrayList<Provider> searchProviderByName(String name) {
        ArrayList<Provider> foundProviders = new ArrayList<>();
        for (Provider provider : providers) {
            if (provider.getName().toLowerCase().contains(name.toLowerCase())) {
                foundProviders.add(provider);
            }
        }
        return foundProviders;

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


    public ArrayList<CommodityDTO> getBuyList(String username) {
        User user = findUserByUsername(username);
        if (user != null) {
            HashMap<Integer, Integer> buylist = user.getBuyList().getItems();
            ArrayList<CommodityDTO> result = new ArrayList<>();
            for (HashMap.Entry<Integer, Integer> item : buylist.entrySet()) {
                Commodity commodity = findCommodityById(item.getKey());

                CommodityDTO buylistItem = new CommodityDTO(commodity.getId(), commodity.getName(),
                        commodity.getProviderId(), commodity.getPrice(), commodity.getCategories(), commodity.getRating(), commodity.getInStock(), item.getValue());

                result.add(buylistItem);
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

    public ArrayList<Commodity> findCommoditiesByProviderId(int providerId) {
        ArrayList<Commodity> commoditiesByProviderId = new ArrayList<>();
        for (Commodity commodity : commodities) {
            if (commodity.getProviderId() == providerId) {
                commoditiesByProviderId.add(commodity);
            }
        }
        return commoditiesByProviderId;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Comment comment = new Comment(username, commodityId, text, dateFormat.format(date));
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

    public void quantityToChangeCommodityInStock(int commodityId, int quantity) {
        for (Commodity commodity : commodities) {
            if (commodity.getId() == commodityId) {
                commodity.setInStock(commodity.getInStock() + quantity);
            }
        }
    }

    public ArrayList<Commodity> getSuggestedCommodities(int commodityId) {
        ArrayList<Commodity> suggestedCommodities = new ArrayList<>(this.commodities);
        Commodity commodity = findCommodityById(commodityId);
        if (commodity == null) {
            return null;
        } else {
            Comparator<Commodity> comparator = (commodity1, commodity2) -> {
                ArrayList<String> currentCategories = commodity.getCategories();
                int is_in_similar_category1 = commodity1.getCategories().containsAll(currentCategories) ? 1 : 0;
                int is_in_similar_category2 = commodity2.getCategories().containsAll(currentCategories) ? 1 : 0;
                float score1 = is_in_similar_category1 * 11 + commodity1.getRating();
                float score2 = is_in_similar_category2 * 11 + commodity2.getRating();
                return Float.compare(score2, score1);
            };
            suggestedCommodities.sort(comparator);
            for (int i = 0; i < suggestedCommodities.size(); i++) {
                if (suggestedCommodities.get(i).getId() == commodity.getId()) {
                    suggestedCommodities.remove(i);
                    break;
                }
            }
            suggestedCommodities = new ArrayList<>(suggestedCommodities.subList(0, 5));
            return suggestedCommodities;
        }

    }
}
