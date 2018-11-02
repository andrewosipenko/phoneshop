package com.es.phoneshop.web.controller.forms;

import org.hibernate.validator.constraints.NotBlank;

public class ClientInformationForm {
    @NotBlank(message="This field is required")
    private String firstName;

    @NotBlank(message="This field is required")
    private String lastName;

    @NotBlank(message="This field is required")
    private String deliveryAddress;

    @NotBlank(message="This field is required")
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
