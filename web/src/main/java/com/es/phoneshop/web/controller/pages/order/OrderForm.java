package com.es.phoneshop.web.controller.pages.order;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderForm {

    @NotEmpty(message = "First name is required")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$", message = "Only cyrillic or Latin character")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$", message = "Only cyrillic or Latin character")
    private String lastName;

    @NotEmpty(message = "Phone number is required")
    @Size(min = 2, max = 20, message = "Phone should be between 2 and 20 characters")
    @Pattern(regexp = "^(\\+)?((\\d{2,3}) ?\\d|\\d)(([ -]?\\d)|( ?(\\d{2,3}) ?)){5,12}\\d$",
            message = "Format number only +(or nothing)country code then operator code and then number")
    private String phone;

    @NotEmpty(message = "Address is required")
    private String address;


    private String additionalInfo;

    public OrderForm() {

    }

    public OrderForm(String firstName, String lastName, String phone, String address, String additionalInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


}
