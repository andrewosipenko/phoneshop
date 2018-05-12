package com.es.phoneshop.web.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderForm {
    private final String ORDER_INFO_ERROR_MESSAGE = "this value is required";

    @NotEmpty(message = ORDER_INFO_ERROR_MESSAGE)
    private String firstName;

    @NotEmpty(message = ORDER_INFO_ERROR_MESSAGE)
    private String lastName;

    @NotEmpty(message = ORDER_INFO_ERROR_MESSAGE)
    private String deliveryAddress;

    @NotEmpty(message = ORDER_INFO_ERROR_MESSAGE)
    private String contactPhoneNo;

    private String additionalInfo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
