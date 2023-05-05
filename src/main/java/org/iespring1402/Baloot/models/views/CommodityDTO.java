package org.iespring1402.Baloot.models.views;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityDTO {
    private Integer id;
    private String name;
    private Integer providerId;
    private String providerName;
    private Integer price;
    private ArrayList<String> categories;
    private Float rating;
    private Integer inStock;
    private Integer quantity;

    public CommodityDTO(
            Integer id,
            String name,
            Integer providerId,
            String providerName,
            Integer price,
            ArrayList<String> categories,
            Float rating,
            Integer inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.providerName = providerName;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.quantity = null;
    }

    public CommodityDTO(
            Integer id,
            String name,
            Integer providerId,
            String providerName,
            Integer price,
            ArrayList<String> categories,
            Float rating) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.providerName = providerName;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = null;
        this.quantity = null;
    }

    public CommodityDTO(
            Integer id,
            String name,
            String providerName,
            Integer price,
            ArrayList<String> categories,
            Float rating,
            Integer inStock) {
        this.id = id;
        this.name = name;
        this.providerId = null;
        this.providerName = providerName;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.quantity = null;
    }

    public CommodityDTO(
            Integer id,
            String name,
            Integer providerId,
            Integer price,
            ArrayList<String> categories,
            Float rating,
            Integer inStock,
            Integer quantity) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.providerName = null;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.quantity = quantity;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }


    public Integer getId() {
        return id;
    }

    
    public Integer getInStock() {
        return inStock;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public Float getRating() {
        return rating;
    }
    public Integer getQuantity() {
        return quantity;
    }
}