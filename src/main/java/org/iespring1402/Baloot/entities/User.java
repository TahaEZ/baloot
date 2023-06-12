package org.iespring1402.Baloot.entities;

import java.util.ArrayList;
import java.util.List;

import org.iespring1402.Baloot.response.FailedResponse;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    public String username;
    public String password;
    public String email;
    public String birthDate;
    public String address;
    public double credit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buy_list_id")
    private BuyList buyList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "used_discounts", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "discount_code_id"))
    private List<DiscountCode> usedDiscounts;

    @ManyToMany
    @JoinTable(name = "purchased_list", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "purchased_item_id"))
    private List<CommodityDTO> purchasedItems = new ArrayList<>();
    
    public void updateUser(String password, String email, String birthDate, String address, double credit) {
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.credit = credit;
    }

    public Response addCredit(long creditToAdd) {
        if (creditToAdd <= 0)
            return new FailedResponse("Invalid credit value!");
        credit += creditToAdd;
        return new SuccessfulResponse();
    }

    public Response addToUsedDiscounts(DiscountCode discount) {
        usedDiscounts.add(discount);
        return new SuccessfulResponse();
    }

    public Response addToBuyList(int commodityId) {
        return buyList.increase(commodityId);
    }


    public Response addToPurchasedList(CommodityDTO commodity) {
        purchasedItems.add(commodity);
        return new SuccessfulResponse();
    }

    public Response removeFromBuyList(int commodityId) {
        return buyList.decrease(commodityId);
    }

    public Response removeItemFromBuyListCompletely(int commodityId) {
        buyList.getItems().remove(commodityId);
        return new SuccessfulResponse();
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

    public double getCredit() {
        return credit;
    }

    public BuyList getBuyList() {
        return buyList;
    }

    public List<CommodityDTO> getPurchasedItems() {
        return purchasedItems;
    }
    public List<DiscountCode> getUsedDiscounts() {
        return usedDiscounts;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setBuyList(BuyList buyList) {
        this.buyList = buyList;
    }

    public void setUsedDiscounts(ArrayList<DiscountCode> usedDiscounts) {
        this.usedDiscounts = usedDiscounts;
    }

    public void setPurchasedItems(List<CommodityDTO> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password
                + "email=" + email + "]";
    }

}