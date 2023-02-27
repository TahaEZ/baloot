package org.iespring1402;

import java.util.*;
import java.util.ArrayList;

public class Baloot {
    ArrayList<User> users;
    ArrayList<Commodity> commodities ;

    public Baloot() {
        this.users = new ArrayList<User>();
        this.commodities = new ArrayList<Commodity>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(User newUser) {
        String username = newUser.username;

        if (!isUsernameValid(username))
            return false;

        boolean alreadyExists = false;

        for (User user : users) {
            if (user.username.equals(username)) {
                alreadyExists = true;

                String password = newUser.password;
                String email = newUser.email;
                String birthDate = newUser.birthDate;
                String address = newUser.address;
                long credit = newUser.credit;

                user.updateUser(password, email, birthDate, address, credit);
                break;
            }
        }

        if (!alreadyExists)
            users.add(newUser);

        return true;
    }

    private boolean isUsernameValid(String username) {
        return !username.matches(".*[@!#$%^&*()\\u0020\\u200C].*"); // false if username contains any special character.
    }

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }

    public boolean IfCommodityExist(int id) {
        if(commodities.isEmpty())
        {
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
}
