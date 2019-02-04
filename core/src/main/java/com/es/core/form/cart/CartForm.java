package com.es.core.form.cart;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartForm {
    List<CartFormItem> cartFormList;

    public List<CartFormItem> getCartFormList() {
        return cartFormList;
    }

    public void setCartFormList(List<CartFormItem> cartFormList) {
        this.cartFormList = cartFormList;
    }
}
