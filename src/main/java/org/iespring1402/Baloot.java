package org.iespring1402;

import java.util.*;
import java.util.ArrayList;

public class Baloot {
    ArrayList<User> users;
    ArrayList<Commodity> commodities;

    public Baloot() {
        this.users = new ArrayList<User>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(String username, String password, String email, String birthDate, String address, long credit) {
        User newUser = new User(username, password, email, birthDate, address, credit);
        users.add(newUser);
    }

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }

    public boolean IfCommodityExist(int id) {
        ListIterator<Commodity> it = commodities.listIterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                return true;
            }
        }
        return false;
    }
}
