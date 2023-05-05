package org.iespring1402.Baloot.models;

import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.iespring1402.Baloot.response.FailedResponse;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public String username;
    public String password;
    public String email;
    public String birthDate;
    public String address;

    public long credit;
    private BuyList buyList;
    private ArrayList<DiscountCode> usedDiscounts;

    private PurchasedList purchasedList;

    public User() {
        super();
        this.buyList = new BuyList();
        this.purchasedList = new PurchasedList();
        this.usedDiscounts = new ArrayList<>();
    }

    public User(String username, String password, String email, String birthDate, String address, long credit) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.credit = credit;
        this.buyList = new BuyList();
        this.purchasedList = new PurchasedList();
        this.usedDiscounts = new ArrayList<>();
    }

    public void updateUser(String password, String email, String birthDate, String address, long credit) {
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.credit = credit;
    }

    public Response addCredit(long creditToAdd){
        if(creditToAdd <= 0)
            return new FailedResponse("Invalid credit value!");
        credit += creditToAdd;
        return new SuccessfulResponse();
    }

    public Response addToUsedDiscounts(DiscountCode discount){
        usedDiscounts.add(discount);
        return new SuccessfulResponse();
    }

    public ArrayList<DiscountCode> getUsedDiscounts() {
        return usedDiscounts;
    }


    public Response addToBuyList(int commodityId) {
        return buyList.increase(commodityId);
    }

    public boolean isDiscountCodeUsed(String discount)
    {
        for(DiscountCode discountCode: usedDiscounts)
        {
          
            if(discountCode.getCode().equals(discount))
            {
                return true;
            }
        }
        return false;
    }

    public Response addToPurchasedList(CommodityDTO commodity){return purchasedList.add(commodity);}
    
    public Response removeFromBuyList(int commodityId) {return buyList.decrease(commodityId);} 
    
    public Response removeItemFromBuyListCompletely(int commodityId){
        buyList.getItems().remove(commodityId);
        return new SuccessfulResponse();
}

    public BuyList getBuyList() {
        return buyList;
    }

    public void resetBuyList()
    {
        buyList = new BuyList();
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public long getCredit() {
        return credit;
    }

    public PurchasedList getPurchasedList() {
        return purchasedList;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }
}
