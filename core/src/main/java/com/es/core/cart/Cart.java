package com.es.core.cart;

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
}
