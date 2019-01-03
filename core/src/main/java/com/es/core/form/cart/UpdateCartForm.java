package com.es.core.form;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateCartForm {
    List<AddCartForm> cartFormList;

    public List<AddCartForm> getCartFormList() {
        return cartFormList;
    }

    public void setCartFormList(List<AddCartForm> cartFormList) {
        this.cartFormList = cartFormList;
    }
}
