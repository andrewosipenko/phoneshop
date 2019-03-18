package com.es.core.model.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private String secureId;
    private List<OrderItem> orderItems = new ArrayList<>();
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;
    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String additionalInfo;
    private String contactPhoneNo;
    private OrderStatus status;
    private Timestamp orderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
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

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", secureId='" + secureId + '\'' +
                ", orderItems=" + orderItems +
                ", subtotal=" + subtotal +
                ", deliveryPrice=" + deliveryPrice +
                ", totalPrice=" + totalPrice +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", contactPhoneNo='" + contactPhoneNo + '\'' +
                ", status=" + status +
                ", orderDate=" + orderDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(secureId, order.secureId) &&
                Objects.equals(orderItems, order.orderItems) &&
                Objects.equals(subtotal, order.subtotal) &&
                Objects.equals(deliveryPrice, order.deliveryPrice) &&
                Objects.equals(totalPrice, order.totalPrice) &&
                Objects.equals(firstName, order.firstName) &&
                Objects.equals(lastName, order.lastName) &&
                Objects.equals(deliveryAddress, order.deliveryAddress) &&
                Objects.equals(additionalInfo, order.additionalInfo) &&
                Objects.equals(contactPhoneNo, order.contactPhoneNo) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secureId, orderItems, subtotal, deliveryPrice,
                totalPrice, firstName, lastName, deliveryAddress, additionalInfo, contactPhoneNo, status);
    }
}

