package com.es.phoneshop.web.response;

import com.es.core.model.cart.Cart;
import org.springframework.validation.ObjectError;

import java.util.List;

public class AddingToCartResponse {
    private String status;
    private Cart cart;
    private List<ObjectError> errors;

    public AddingToCartResponse() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "AddingToCartResponse{" +
                "status='" + status + '\'' +
                ", cart=" + cart +
                ", errors=" + errors +
                '}';
    }
}
