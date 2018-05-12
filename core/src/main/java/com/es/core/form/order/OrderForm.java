package com.es.core.form.order;

import com.es.core.validation.annotation.PhoneNumber;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderForm {
    private List<OrderFormItem> orderFormItems;

    @Size(min = 2, max = 20, message = "Size should be between 2 and 20")
    private String firstName;
    @Size(min = 2, max = 20, message = "Size should be between 2 and 20")
    private String lastName;
    @Size(min = 1, message = "Value is required")
    private String deliveryAddress;
    @PhoneNumber
    private String contactPhoneNo;
    private String extraInfo;

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

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
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
