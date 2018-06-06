package com.es.phoneshop.web.controller.beans;

public class CartFormData extends QuantityForm{

    private boolean toDelete;

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public boolean isToDelete() {
        return toDelete;
    }
}
