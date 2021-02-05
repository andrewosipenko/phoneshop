package com.es.service;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.AddingToCartModel;
import com.es.core.model.CartItemModel;
import com.es.core.model.CartModel;

import java.util.List;
import java.util.Map;

public interface CartService {

    CartModel getCart();

    void addPhone(AddingToCartModel addingModel) throws OutOfStockException;

    void update(Map<Long, String> itemsWithNewQnt, List<CartItemModel> cartItemModels);

    void remove(Long phoneId);
}
