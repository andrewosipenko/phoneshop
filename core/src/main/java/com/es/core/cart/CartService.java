package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.util.Map;

public interface CartService {
    Cart getCart();
    void addPhone(Long phoneId, Long quantity);
    void remove(Long phoneId);
    long getCountItems();
    Map<Phone, Long> getAllItems();
    void update(Map<Long, Long> phoneWithQuantity);
    void removeProductsWhichNoInStock();
    void emptyCart();
}
