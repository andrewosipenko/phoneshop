package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    Cart getCart();
    void addPhone(Long phoneId, Long quantity, String color);
    void remove(Long phoneId, String color);
    long getCountItems();
    BigDecimal getPrice();
    List<Phone> getAllItems();
}
