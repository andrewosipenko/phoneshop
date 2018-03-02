package com.es.phoneshop.web.bean.cart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class CartUpdateInfo implements Serializable {

    @Valid
    @NotNull
    private List<CartItem> cartItems;

    public CartUpdateInfo() {
    }

    public CartUpdateInfo(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
