package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;

import java.util.ArrayList;

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
        return buyList.add(commodityId);
    }

    public boolean isDiscountCodeUsed(String discount)
    {
        for(DiscountCode discountCode: usedDiscounts)
        {
            if(discountCode.getCode() == discount)
            {
                return true;
            }
        }
        return false;
    }

    public void setActiveDiscountCode(String discountCode)
    {
            for(DiscountCode foundDiscountCode : Baloot.getInstance().getDiscountCodes())
            {
                if(foundDiscountCode.getCode() == discountCode)
                {
                    buyList.setActiveDiscountCode(foundDiscountCode);
                }
            }
    }
    public Response addToPurchasedList(Commodity commodity){return purchasedList.add(commodity);}

    public Response removeFromBuyList(int commodityId) {return buyList.remove(commodityId);}

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
