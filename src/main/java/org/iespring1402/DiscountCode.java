package org.iespring1402;

public class DiscountCode {
    private String code;
    private int discount;

    private boolean deprecated;

    public DiscountCode(String codeName, int percentage) {
        code = codeName;
        discount = percentage;
        deprecated = false;
    }

    public int getDiscount() {
        return discount;
    }

    public String getCode() {
        return code;
    }

    public boolean getDeprecated(){
        return deprecated;
    }
    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

}
