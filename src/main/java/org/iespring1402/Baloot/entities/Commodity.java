package org.iespring1402.Baloot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "commodities")
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int providerId;
    private int price;

    @ElementCollection
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "commodity_id"))
    @Column(name = "categories")
    private List<String> categories;
    private float rating;
    private int inStock;
    private String image;

    @ElementCollection
    @CollectionTable(name = "commodity_ratings")
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "rating")
    private Map<String, Integer> ratings;

    public Commodity() {
        super();
        this.ratings = new HashMap<>();
    }

    public Commodity(int id, String name, int providerId, int price, ArrayList<String> categories, float rating,
            int inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.ratings = new HashMap<>();
    }

    public Commodity(int id, String name, int providerId, int price, ArrayList<String> categories, float rating,
            int inStock, String image) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.ratings = new HashMap<>();
        this.image = image;
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

    public List<String> getCategories() {
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
    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String img) {
        this.image = img;
    }
}
