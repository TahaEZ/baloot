package org.iespring1402.Baloot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;

public class Commodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private ArrayList<String> categories;
    private float rating;
    private int inStock;

    private HashMap<String, Integer> ratings;

    public Commodity() {
        super();
        this.ratings = new HashMap<>();
    }

    public Commodity(int id, String name, int providerId, int price, ArrayList<String> categories, float rating, int inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.ratings = new HashMap<>();
    }

    public void addRating(String username, int score) {
        ratings.put(username, score);
        updateRating();
    }

    private void updateRating() {
        float sum = 0;

        for (int rating : ratings.values()) {
            sum += rating;
        }

        this.rating = sum / ratings.size();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public int getProviderId() {
        return providerId;
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

    public int getInStock() {
        return inStock;
    }

    public boolean isInStock() {
        return inStock != 0;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    @JsonIgnore
    public HashMap<String, Integer> getRatings() {
        return ratings;
    }
}
