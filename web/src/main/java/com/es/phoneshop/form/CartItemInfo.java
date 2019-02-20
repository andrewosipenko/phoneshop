package com.es.phoneshop.form;

public class CartItemInfo {
    private long phoneId;
    private long quantity;

    public CartItemInfo() {

    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemInfo{" +
                "phoneId=" + phoneId +
                ", quantity=" + quantity +
                '}';
    }
}
