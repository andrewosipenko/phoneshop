package com.es.phoneshop.web.form;

import java.util.Arrays;

public class OrderInfo {
    private String name;
    private String lastName;
    private String address;
    private String phone;
    private String additionalInfo;
    private Long[] quantity;

    public OrderInfo() {

    }

    public OrderInfo(String name,
                     String lastName,
                     String address,
                     String phone,
                     String additionalInfo,
                     Long[] quantity) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.additionalInfo = additionalInfo;
        this.quantity = quantity;
    }

    public Long[] getQuantity() {
        return quantity;
    }

    public void setQuantity(Long[] quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", quantity=" + Arrays.toString(quantity) +
                '}';
    }
}
