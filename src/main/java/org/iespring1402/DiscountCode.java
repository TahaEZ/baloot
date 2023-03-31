package org.iespring1402;

public class DiscountCode {
    private String discountCode;
    private int discount;
    private boolean deprecated;

    public DiscountCode(String discountCode, int discount) {
        this.discountCode = discountCode;
        this.discount = discount;
        deprecated = false;
    }

    public int getDiscount() {
        return discount;
    }

    public String getCode() {
        return discountCode;
    }

    public boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

}
