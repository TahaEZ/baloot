package org.iespring1402.Baloot.model;

import java.util.ArrayList;

public class CommodityByIdView {
    private int id;
    private String name;
    private String provider;
    private int price;
    private ArrayList<String> categories;
    private float rating;

    public CommodityByIdView(int id, String name, String provider, int price, ArrayList<String> categories, float rating) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public float getRating() {
        return rating;
    }
}
