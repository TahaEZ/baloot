package org.iespring1402;

public class DiscountCode {
    private String discountCode;
    private int discount;
    private boolean deprecated;
    public DiscountCode()
    {
        super();
    }

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
