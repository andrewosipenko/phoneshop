package com.es.core.model.order;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private Long id;
    private List<OrderItem> orderItems;
    /**
     * A sum of order item prices;
     */
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    /**
     * <code>subtotal</code> + <code>deliveryPrice</code>
     */
    private BigDecimal totalPrice;

    @NotEmpty(message = "{order.notEmpty}")
    @Length(message = "{order.maxLength}", max = 254)
    private String firstName;

    @NotEmpty(message = "{order.notEmpty}")
    @Length(message = "{order.maxLength}", max = 254)
    private String lastName;

    @NotEmpty(message = "{order.notEmpty}")
    @Length(message = "{order.maxLength}", max = 500)
    private String deliveryAddress;

    @NotEmpty(message = "{order.notEmpty}")
    @Length(message = "{order.maxLength}", max = 30)
    private String contactPhoneNo;

    @Length(message = "{order.maxLength}", max = 4096)
    private String additionalInfo;

    private OrderStatus status = OrderStatus.NEW;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String description) {
        this.additionalInfo = description;
    }
}
