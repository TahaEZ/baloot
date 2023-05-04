package org.iespring1402.Baloot.models.views;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityDTO {
    private int id;
    private String name;
    private int providerId;
    String providerName;
    private int price;
    private ArrayList<String> categories;
    private float rating;
    private int inStock;

    public CommodityDTO(
            int id,
            String name,
            int providerId,
            String providerName,
            int price,
            ArrayList<String> categories,
            float rating,
            int inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.providerName = providerName;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

    public CommodityDTO(
            int id,
            String name,
            int providerId,
            String providerName,
            int price,
            ArrayList<String> categories,
            float rating) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.providerName = providerName;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
    }

    public CommodityDTO(
            int id,
            String name,
            String providerName,
            int price,
            ArrayList<String> categories,
            float rating,
            int inStock) {
        this.id = id;
        this.name = name;
        this.providerName = providerName;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
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

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public int getId() {
        return id;
    }

    public int getInStock() {
        return inStock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public float getRating() {
        return rating;
    }
}
