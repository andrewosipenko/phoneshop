package com.es.phoneshop.web.controller.pages;

public class AddProductToCartForm {

    private Long phoneId;
    private String quantity;

    public AddProductToCartForm() {

    }

    public AddProductToCartForm(Long id, String quantity) {
        this.phoneId = id;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
