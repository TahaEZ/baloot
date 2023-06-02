package org.iespring1402.Baloot.entities;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "discounts")
public class DiscountCode {
    @Id
    @NaturalId
    private String discountCode;
    private double discount;
    private boolean deprecated;

    public DiscountCode() {
        super();
    }

    public DiscountCode(String discountCode, Double discount) {
        this.discountCode = discountCode;
        this.discount = discount;
        deprecated = false;
    }

    public double getDiscount() {
        return discount;
    }

    public String getCode() {
        return discountCode;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

}
