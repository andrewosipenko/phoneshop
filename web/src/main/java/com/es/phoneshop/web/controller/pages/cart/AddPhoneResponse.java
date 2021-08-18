package com.es.phoneshop.web.controller.pages.cart;

import com.es.core.model.cart.InfoCart;
import java.util.List;

public class AddPhoneResponse {

    private InfoCart infoCart;

    private Boolean valid;

    private List<String> errors;

    public InfoCart getInfoCart() {
        return infoCart;
    }

    public void setInfoCart(InfoCart infoCart) {
        this.infoCart = infoCart;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
