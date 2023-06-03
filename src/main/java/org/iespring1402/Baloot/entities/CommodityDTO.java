package org.iespring1402.Baloot.entities;

import java.util.ArrayList;



import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "purchased_item")
public class CommodityDTO {


  

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer providerId;
    private Integer price;

    @ElementCollection
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "commodity_id"))
    @Column(name = "categories")
    private ArrayList<String> categories;
    private Float rating;
    private Integer inStock;
    private Integer quantity;
    private String image;

    public ArrayList<String> getCategories() {
        return categories;
    }
    public Integer getId() {
        return id;
    }
    public String getImage() {
        return image;
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
    public Integer getQuantity() {
        return quantity;
    }
    public Float getRating() {
        return rating;
    }
    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setImage(String image) {
        this.image = image;
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
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public void setRating(Float rating) {
        this.rating = rating;
    }

    
    // constructors, getters and setters
}
