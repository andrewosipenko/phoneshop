package com.es.phoneshop.web.response;

import org.springframework.validation.ObjectError;

import java.math.BigDecimal;
import java.util.List;

public class AddingToCartResponse {
    private String status;
    private List<ObjectError> errors;
    private int countOfCartItems;
    private BigDecimal totalPrice;

    public AddingToCartResponse() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    public int getCountOfCartItems() {
        return countOfCartItems;
    }

    public void setCountOfCartItems(int countOfCartItems) {
        this.countOfCartItems = countOfCartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "AddingToCartResponse{" +
                "status='" + status + '\'' +
                ", errors=" + errors +
                ", countOfCartItems=" + countOfCartItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
