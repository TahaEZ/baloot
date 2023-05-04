package org.iespring1402.Baloot.models;

import java.util.HashMap;

public class Provider {
    private int id;
    private String name;
    private String registryDate;
    private HashMap<Integer, Float> ratings;

    public Provider() {
        super();
        this.ratings = new HashMap<>();
    }

    public Provider(int id, String name, String registryDate) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
        this.ratings = new HashMap<>();
    }

    public void addRating(int commodityId, float rating) {
        ratings.put(commodityId, rating);
    }

    public void updateProvider(String name, String registryDate, HashMap<Integer, Float> ratings) {
        this.name = name;
        this.registryDate = registryDate;
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public HashMap<Integer, Float> getRatings() {
        return ratings;
    }
}
