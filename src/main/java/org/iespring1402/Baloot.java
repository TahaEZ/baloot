package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;
import org.iespring1402.views.CommodityNoInStock;

import java.util.*;
import java.util.ArrayList;

public class Baloot {
    private static Baloot instance;
    private ArrayList<User> users;
    private ArrayList<Commodity> commodities;

    private ArrayList<Provider> providers;

    public Baloot() {
        this.users = new ArrayList<User>();
        this.commodities = new ArrayList<Commodity>();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.commodities.add(new Commodity(4, "Onion", 3, 200, // TODO: mock data - should be removed later
                new ArrayList<>(Arrays.asList("Vegetables")), 8.3F, 1000));
        this.commodities.add(new Commodity(1, "Headphone A", 1, 5200,
                new ArrayList<>(Arrays.asList("Technology", "Headphone")), 8.3F, 20));
        this.commodities.add(new Commodity(2, "Headphone B", 1, 3200,
                new ArrayList<>(Arrays.asList("Technology", "Headphone")), 10F, 0));
        this.commodities.add(new Commodity(3, "Bugatti", 2, 10000000,
                new ArrayList<>(Arrays.asList("Car")), 8.3F, 2));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.providers = new ArrayList<Provider>();
    }

    public static Baloot getInstance() {
        if (instance == null)
            instance = new Baloot();
        return instance;
    }

    User findUserByUsername(String username) {
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

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
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
            int providerId = commodity.getProviderId();
            Provider provider = findProviderByProviderId(providerId);
            if (provider != null) {
                commodity.addRating(username, score);

                float commodityRating = commodity.getRating();
                provider.addRating(commodityId, commodityRating);

                return new SuccessfulResponse();
            } else
                return new FailedResponse("No provider found for this commodity!");
        } else
            return new FailedResponse("No commodity found with this commodity id!");
    }

    Provider findProviderByProviderId(int providerId) {
        for (Provider provider : providers) {
            if (provider.getId() == providerId) {
                return provider;
            }
        }
        return null;
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
}
