package org.iespring1402.Baloot.entities;

import java.util.ArrayList;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchased_list")
public class PurchasedList {

    @Id
    @NaturalId
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToMany(mappedBy = "purchasedList", cascade = CascadeType.ALL)
    private ArrayList<CommodityDTO> purchasedItems = new ArrayList<>();

    public ArrayList<CommodityDTO> getPurchasedItems() {
        return purchasedItems;
    }
    public void setPurchasedItems(ArrayList<CommodityDTO> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }
}