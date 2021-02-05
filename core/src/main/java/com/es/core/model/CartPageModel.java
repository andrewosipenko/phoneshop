package com.es.core.model;

import java.util.HashMap;
import java.util.Map;

public class CartPageModel {

    private Map<Long, String> cartEntities;

    public CartPageModel() {
        cartEntities = new HashMap<>();
    }

    public Map<Long, String> getCartInfoModels() {
        return cartEntities;
    }

    public void setCartInfoModels(Map<Long, String> cartEntities) {
        this.cartEntities = cartEntities;
    }
}
