package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;

public class User {
    public String username;
    public String password;
    public String email;
    public String birthDate;
    public String address;
    public long credit;
    private BuyList buyList;

    private PurchasedList purchasedList;

    public User() {
        super();
        this.buyList = new BuyList();
        this.purchasedList = new PurchasedList();
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

    public Response addToBuyList(int commodityId) {
        return buyList.add(commodityId);
    }

    public Response addToPurchasedList(Commodity commodity){return purchasedList.add(commodity);}

    public Response removeFromBuyList(int commodityId) {return buyList.remove(commodityId);}

    public BuyList getBuyList() {
        return buyList;
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
