package com.es.core.form.order;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderForm {
    private List<OrderFormItem> orderFormItems;
    @NotNull
    @Size(min = 1, max = 35, message = "First name's size should be between 1 and 35")
    private String firstName;
    @NotNull
    @Size(min = 1, max = 35, message = "Last name's size should be between 1 and 35")
    private String lastName;
    @NotNull
    @Size(min = 1, message = "Delivery address is a required field")
    private String deliveryAddress;
    @NotNull
    @Pattern(regexp = "^\\+375(17|29|33|44)[0-9]{7}$", message = "Phone number must be in the format +375(17|29|33|44)xxxxxxx")
    private String contactPhoneNo;
    private String additionalInformation;
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;

    public List<OrderFormItem> getOrderFormItems() {
        return orderFormItems;
    }

    public void setOrderFormItems(List<OrderFormItem> orderFormItems) {
        this.orderFormItems = orderFormItems;
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

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
