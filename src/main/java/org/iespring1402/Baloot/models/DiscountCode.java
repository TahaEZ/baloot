package org.iespring1402.Baloot.models;

public class DiscountCode {
    private String discountCode;
    private double discount;
    private boolean deprecated;
    public DiscountCode()
    {
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
