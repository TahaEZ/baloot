package org.iespring1402.views;

import java.util.ArrayList;
import java.util.HashMap;

public class CommodityNoInStock {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private ArrayList<String> categories;
    private float rating;


    public ArrayList<String> getCategories() {
        return categories;
    }

    public int getProviderId() {
        return providerId;
    }

    public float getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


}

