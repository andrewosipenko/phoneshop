package com.es.phoneshop.web.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserContactInfoRequest {
    public static final String PLEASE_INSERT_FIRST_NAME = "Please, insert first name";
    public static final String PLEASE_INSERT_SECOND_NAME = "Please, insert second name";
    public static final String PLEASE_INSERT_DELIVERY_ADDRESS = "Please, insert delivery address";
    public static final String PLEASE_INSERT_PHONE_NUMBER = "Please, insert phone number";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number, pattern is +###(17|29|33|44)#######";
    public static final String PHONE_PATTERN = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";
    @NotNull(message = PLEASE_INSERT_FIRST_NAME)
    @Size(min = 1, message = PLEASE_INSERT_FIRST_NAME)
    private String firstName;

    @NotNull(message = PLEASE_INSERT_SECOND_NAME)
    @Size(min = 1, message = PLEASE_INSERT_SECOND_NAME)
    private String lastName;

    @NotNull(message = PLEASE_INSERT_DELIVERY_ADDRESS)
    @Size(min = 1, message = PLEASE_INSERT_DELIVERY_ADDRESS)
    private String deliveryAddress;

    @NotNull(message = PLEASE_INSERT_PHONE_NUMBER)
    @Size(message = PLEASE_INSERT_PHONE_NUMBER)
    @Pattern(regexp = PHONE_PATTERN, message = INVALID_PHONE_NUMBER)
    private String contactPhoneNo;

    private String additionalInfo;

    public UserContactInfoRequest() {
    }

    public UserContactInfoRequest(String firstName, String lastName, String deliveryAddress, String contactPhoneNo, String additionalInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
        this.contactPhoneNo = contactPhoneNo;
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

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
}
