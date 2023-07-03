package org.iespring1402.Baloot.entities;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.response.FailedResponse;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "buylists")
public class BuyList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ElementCollection
    @CollectionTable(name = "buylist_record", joinColumns = {
            @JoinColumn(name = "buylist_id", referencedColumnName = "id") })
    @MapKeyColumn(name = "item_id")
    @Column(name = "item_quantity")
    private Map<Integer, Integer> items = new HashMap<Integer, Integer>();
    private boolean isDiscountActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buylist_active_discount")
    private DiscountCode activeDiscountCode;

    public BuyList() {
        items = new HashMap<Integer, Integer>();
        isDiscountActive = false;
    }


    public Response increase(int commodityId) {

        for (Map.Entry<Integer, Integer> item : items.entrySet()) {
            if (item.getKey() == commodityId) {
                item.setValue(item.getValue() + 1);
                return new SuccessfulResponse();
            }
        }
        items.put(commodityId, 1);
        return new SuccessfulResponse();
    }

    public Response decrease(int commodityId) {

        for (Map.Entry<Integer, Integer> item : items.entrySet()) {
            if (item.getKey() == commodityId) {
                if (item.getValue() - 1 == 0) {
                    items.remove(item.getKey());
                    return new SuccessfulResponse();
                }
                item.setValue(item.getValue() - 1);
                return new SuccessfulResponse();
            }
        }
        return new FailedResponse();
    }

    public Response remove(int commodityId) {
        items.remove(commodityId);
        return new SuccessfulResponse();
    }

    public void setDiscountActive(boolean discountActive) {
        isDiscountActive = discountActive;
    }

    public void setActiveDiscountCode(DiscountCode activeDiscountCode) {
        this.activeDiscountCode = new DiscountCode(activeDiscountCode.getCode(), activeDiscountCode.getDiscount());
        setDiscountActive(true);
    }

    public boolean isDiscountActive() {
        return isDiscountActive;
    }

    public DiscountCode getActiveDiscountCode() {
        return activeDiscountCode;
    }

    public void deactivateDiscountCode() {
        activeDiscountCode = new DiscountCode();
        isDiscountActive = false;
    }

    public double totalCost() {
        double total = 0;
        for (HashMap.Entry<Integer, Integer> item : items.entrySet()) {
            int cost = Baloot.getInstance().findCommodityById(item.getKey()).getPrice();
            total += cost * item.getValue();
        }
        if (isDiscountActive == true) {
            total = total - total * activeDiscountCode.getDiscount() / 100;
        }
        return total;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }
}
