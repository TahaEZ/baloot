package org.iespring1402.Baloot.models;

import org.iespring1402.Baloot.response.FailedResponse;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;

import java.util.HashMap;

public class BuyList {
    private HashMap<Integer, Integer> items;
    private boolean isDiscountActive;
    private DiscountCode activeDiscountCode;

    public BuyList() {
        items = new HashMap<Integer,Integer>();
        isDiscountActive = false;
    }

    public Response increase(int commodityId) {

        for(HashMap.Entry<Integer , Integer>item : items.entrySet())
        {
            if (item.getKey() == commodityId) {
                item.setValue(item.getValue()+1);
                return new SuccessfulResponse();
            }
        }
        items.put(commodityId,1);
        return new SuccessfulResponse();
    }

    public Response decrease(int commodityId) {

        for (HashMap.Entry<Integer, Integer> item : items.entrySet()) {
            if (item.getKey() == commodityId) {
                if(item.getValue() -1 == 0)
                {
                    items.remove(item.getKey());
                    return new SuccessfulResponse();
                }
                item.setValue(item.getValue() -1);
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

    public long totalCost() {
        long total = 0;
        for (HashMap.Entry<Integer,Integer> item :items.entrySet()) {
            int cost = Baloot.getInstance().findCommodityById(item.getKey()).getPrice();
            total += cost * item.getValue();
        }
        if (isDiscountActive == true) {
            total = total - total * activeDiscountCode.getDiscount() / 100;
        }
        return total;
    }

    public HashMap<Integer, Integer> getItems() {
        return items;
    }
}
