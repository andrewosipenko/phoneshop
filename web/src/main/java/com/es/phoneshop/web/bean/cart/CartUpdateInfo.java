package com.es.phoneshop.web.bean.cart;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class CartUpdateInfo implements Serializable {

    @Valid
    private List<CartPhoneInfo> cartPhoneInfos;

    public List<CartPhoneInfo> getCartPhoneInfos() {
        return cartPhoneInfos;
    }

    public void setCartPhoneInfos(List<CartPhoneInfo> cartPhoneInfos) {
        this.cartPhoneInfos = cartPhoneInfos;
    }
}
