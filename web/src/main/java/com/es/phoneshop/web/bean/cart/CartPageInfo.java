package com.es.phoneshop.web.bean.cart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CartPageInfo {
    @Valid
    @NotNull
    List<CartItemInfo> cartItemInfo;

    public CartPageInfo() {

    }

    public CartPageInfo(List<CartItemInfo> cartItemInfo) {
        this.cartItemInfo = cartItemInfo;
    }

    public List<CartItemInfo> getCartItemInfo() {
        return cartItemInfo;
    }

    public void setCartItemInfo(List<CartItemInfo> cartItemInfo) {
        this.cartItemInfo = cartItemInfo;
    }
}
