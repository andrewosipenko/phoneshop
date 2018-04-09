package com.es.phoneshop.core.cart.model;

import com.es.phoneshop.core.phone.model.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(Phone phone, Long quantity) {
        Optional<CartItem> item = items.stream()
                .filter(cartItem -> cartItem.getPhone().getId().equals(phone.getId()))
                .findFirst();
        if (item.isPresent())
            item.get().setQuantity(item.get().getQuantity() + quantity);
        else
            items.add(new CartItem(phone, quantity));
    }
}
