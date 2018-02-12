package com.es.core.cart;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    /**
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    private Map<Long,Long> items;

    public Cart(){
        items = new HashMap<>();
    }

    public Map<Long, Long> getItems() {
        return items;
    }

    public void setItems(Map<Long, Long> items) {
        this.items = items;
    }

    public void addPhone(Long phoneId, Long quantity){
        items.merge(phoneId,quantity,(oldQuantity, newQuantity) -> oldQuantity + newQuantity);
    }
}
