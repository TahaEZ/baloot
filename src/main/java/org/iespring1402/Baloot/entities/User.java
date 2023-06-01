package org.iespring1402.Baloot.entities;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @JoinTable(name = "user_discounts", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "discount_code_id"))
    private ArrayList<DiscountCode> usedDiscounts;

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

    public ArrayList<DiscountCode> getUsedDiscounts() {
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

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password
                + "email=" + email + "]";
    }

}