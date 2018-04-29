package com.es.phoneshop.core.order.throwable;

import com.es.phoneshop.core.phone.model.Phone;

import java.util.List;

public class OutOfStockException extends Exception {
    private List<Phone> rejectedPhones;

    public OutOfStockException(List<Phone> rejectedPhones) {
        this.rejectedPhones = rejectedPhones;
    }

    public List<Phone> getRejectedPhones() {
        return rejectedPhones;
    }
}
