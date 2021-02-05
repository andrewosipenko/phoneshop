package com.es.core.populator.impl;

import com.es.core.model.CartModel;
import com.es.core.data.CartData;
import com.es.core.populator.interfaces.Populator;

public class CartModelPopulator implements Populator<CartModel, CartData> {

    @Override
    public void populate(CartModel source, CartData target) {
        target.setTotalCost(source.getTotalCost());
        target.setTotalQuantity(source.getTotalQuantity());
        target.setCartItems(source.getCartItems());
    }
}
