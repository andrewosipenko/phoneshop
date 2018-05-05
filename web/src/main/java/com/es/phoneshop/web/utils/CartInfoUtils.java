package com.es.phoneshop.web.utils;

import com.es.core.cart.Cart;
import com.es.phoneshop.web.bean.cart.CartItemInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartInfoUtils {
    public static List<CartItemInfo> getCartItemInfoList(Cart cart) {
        if(cart.getItems().size() == 0)
            return new ArrayList<>();
        return cart.getItems()
                .stream()
                .map(e -> new CartItemInfo(e.getPhone().getId(), e.getQuantity()))
                .collect(Collectors.toList());
    }

    public static Map<Long, Long> getCartUpdateInfoMap(List<CartItemInfo> cartItemInfo) {
        return cartItemInfo.stream()
                .collect(Collectors.toMap(CartItemInfo::getPhoneId, CartItemInfo::getQuantity));
    }
}
