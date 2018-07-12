package com.es.core.model.order;

public enum OrderStatus {
    NEW, DELIVERED, REJECTED;

    public boolean isNew(){
        return this == NEW;
    }
}
