package org.iespring1402;

import java.util.ArrayList; // import the ArrayList class

public class Commodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private ArrayList<String> categories;
    private float rating;
    private int inStock;

    public Commodity() {
        super();
    }

    public Commodity(int id, String name, int providerId, int price, ArrayList<String> categories, float rating, int inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
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

}
