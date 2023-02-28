package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;

import java.util.*;
import java.util.ArrayList;

public class Baloot {
    ArrayList<User> users;
    ArrayList<Commodity> commodities;

    public Baloot() {
        this.users = new ArrayList<User>();
        this.commodities = new ArrayList<Commodity>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    User findUserByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
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

    Commodity findCommodityById(int commodityId) {
        for (Commodity commodity : commodities) {
            if (commodity.getId() == commodityId) {
                return commodity;
            }
        }
        return null;
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
            if (commodity == null)
                return new FailedResponse("No commodity found with this commodity id!");
            else if (!commodity.isInStock())
                return new FailedResponse("This commodity is out of stock!");
            else
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

    public ArrayList<Map<String, Object>> getBuyList(String username) {
        User user = findUserByUsername(username);

        if (user != null) {
            ArrayList<Integer> BuyList = user.getBuyList().getList();
            ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            for (int commodityId : BuyList) {
                Commodity commodity = findCommodityById(commodityId);

                Map<String, Object> commodityWithNoInStockField = new HashMap<>();
                commodityWithNoInStockField.put("id", commodity.getId());
                commodityWithNoInStockField.put("name", commodity.getName());
                commodityWithNoInStockField.put("providerId", commodity.getProviderId());
                commodityWithNoInStockField.put("price", commodity.getPrice());
                commodityWithNoInStockField.put("categories", commodity.getCategories());
                commodityWithNoInStockField.put("rating", commodity.getRating());

                result.add(commodityWithNoInStockField);
            }
            return result;
        } else
            return null;
    }

    public Response rateCommodity(String username, int commodityId, int score) {
        Commodity commodity = findCommodityById(commodityId);
        if (commodity != null) {
            commodity.addRating(username, score);
            return new SuccessfulResponse();
        } else
            return new FailedResponse("No commodity found with this commodity id!");
    }
}
