package org.iespring1402.Baloot.entities;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String registryDate;
    @ElementCollection
    @CollectionTable(name = "provider_ratings", joinColumns = {
            @JoinColumn(name = "provider_id", referencedColumnName = "id") })
    @MapKeyColumn(name = "commodity_id")
    @Column(name = "commodity_rating")
    private Map<Integer, Float> ratings;
    @Column(length = 500)
    private String image;

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

    public Provider(int id, String name, String registryDate, String image) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
        this.ratings = new HashMap<>();
        this.image = image;
    }

    public void addRating(int commodityId, float rating) {
        ratings.put(commodityId, rating);
    }

    public void updateProvider(String name, String registryDate, Map<Integer, Float> ratings) {
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

    public Map<Integer, Float> getRatings() {
        return ratings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
