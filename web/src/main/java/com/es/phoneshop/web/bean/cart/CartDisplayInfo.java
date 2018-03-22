package com.es.phoneshop.web.bean.cart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class CartDisplayInfo implements Serializable {

    @Valid
    @NotNull
    private List<CartDisplayItem> cartDisplayItems;

    public CartDisplayInfo() {
    }

    public CartDisplayInfo(List<CartDisplayItem> cartDisplayItems) {
        this.cartDisplayItems = cartDisplayItems;
    }

    public List<CartDisplayItem> getCartDisplayItems() {
        return cartDisplayItems;
    }

    public void setCartDisplayItems(List<CartDisplayItem> cartDisplayItems) {
        this.cartDisplayItems = cartDisplayItems;
    }
}
