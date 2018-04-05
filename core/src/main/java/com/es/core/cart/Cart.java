package com.es.core.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Cart {
    private Map<Long, CartEntry> products;

    Cart(){
        this.products = new Hashtable<>();
    }

    public Map<Long, CartEntry> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, CartEntry> products){
        this.products = products;
    }

    public BigDecimal getBill(){
        return products.values().stream()
                .map(CartEntry::obtainCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    public Long getQuantity(){
        return products.values()
                .stream()
                .mapToLong(CartEntry::getQuantity)
                .reduce(Long::sum)
                .orElse(0L);
    }
}
