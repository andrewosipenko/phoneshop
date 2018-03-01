package com.es.phoneshop.web.bean.cart;

public class CartFieldError {

    private String fieldName;

    private String previousValue;

    private String errorMessage;

    public CartFieldError() {
    }

    public CartFieldError(String fieldName, String previousValue, String errorMessage) {
        this.fieldName = fieldName;
        this.previousValue = previousValue;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
