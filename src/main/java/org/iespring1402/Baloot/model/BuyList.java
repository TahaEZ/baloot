package org.iespring1402.Baloot.model;

import org.iespring1402.Baloot.response.FailedResponse;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;

import java.util.ArrayList;

public class BuyList {
    private ArrayList<Integer> list;

    private boolean isDiscountActive;
    private DiscountCode activeDiscountCode;
    public BuyList() {
        list = new ArrayList<Integer>();
        isDiscountActive = false;
    }

    public Response add(int commodityId) {
        if (list.contains(commodityId)) {
            return new FailedResponse("This commodity already exists in your buy list!");
        }

        list.add(commodityId);
        return new SuccessfulResponse();
    }

    public Response remove(int commodityId) {
        int index = list.indexOf(commodityId);
        if (index != -1) {
            list.remove(index);
            return new SuccessfulResponse();
        } else return new FailedResponse("No commodity found with this commodity id in your buy list!");
    }

    public void setDiscountActive(boolean discountActive) {
        isDiscountActive = discountActive;
    }

    public void setActiveDiscountCode(DiscountCode activeDiscountCode) {
        this.activeDiscountCode = new DiscountCode(activeDiscountCode.getCode(),activeDiscountCode.getDiscount());
        setDiscountActive(true);
    }

    public boolean isDiscountActive() {
        return isDiscountActive;
    }

    public DiscountCode getActiveDiscountCode() {
        return activeDiscountCode;
    }
    public  void deactivateDiscountCode(){
        activeDiscountCode = new DiscountCode();
        isDiscountActive = false;
    }
    public long totalCost(){
        long total = 0;
        for(int id : list)
        {
            int cost = Baloot.getInstance().findCommodityById(id).getPrice();
            total += cost;
        }
        if(isDiscountActive == true )
        {
            total = total - total* activeDiscountCode.getDiscount()/100;
        }
        return total;
    }

    public ArrayList<Integer> getList() {
        return list;
    }
}
