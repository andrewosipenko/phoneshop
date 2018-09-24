package com.es.phoneshop.core.cart;

import com.es.phoneshop.core.model.phone.Phone;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    /**
     * @param items
     * key: {@link Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);

    long getItemsNum();

    long getItemNumById(Long phoneId);

    int getOverallPrice();

    boolean isNotEnoughStock(Long phoneId, Long quantity);

    Map<Phone,Long> getPhoneMap();

    void setColors(Phone phone);
}
