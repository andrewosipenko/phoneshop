package com.es.phoneshop.web.model.order;

import org.hibernate.validator.constraints.NotEmpty;

public class PersonInfo {
    @NotEmpty(message = "{firstName.empty}")
    private String firstName;

    @NotEmpty(message = "{lastName.empty}")
    private String lastName;

    @NotEmpty(message = "{address.empty}")
    private String address;

    @NotEmpty(message = "{phone.empty}")
    private String phone;

    private String additionalInfo;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
