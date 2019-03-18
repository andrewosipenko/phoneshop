package com.es.core.util;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class OrderCreatorFromResultSet {
    private final static String ID = "orderId";
    private final static String SECURE_ID = "secureId";
    private final static String NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String SUBTOTAL = "subtotal";
    private final static String DELIVERY_PRICE = "deliveryPrice";
    private final static String TOTAL_PRICE = "totalPrice";
    private final static String DELIVERY_ADDRESS = "deliveryAddress";
    private final static String ADDITIONAL_INFO = "additionalInfo";
    private final static String CONTACT_PHONE_NO = "contactPhoneNo";
    private final static String STATUS = "orderStatus";
    private final static String ORDER_DATE = "orderDate";

    private OrderCreatorFromResultSet() {

    }

    public static Order createOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong(ID));
        order.setSecureId(resultSet.getString(SECURE_ID));
        order.setFirstName(resultSet.getString(NAME));
        order.setLastName(resultSet.getString(LAST_NAME));
        order.setSubtotal(resultSet.getBigDecimal(SUBTOTAL));
        order.setDeliveryPrice(resultSet.getBigDecimal(DELIVERY_PRICE));
        order.setTotalPrice(resultSet.getBigDecimal(TOTAL_PRICE));
        order.setDeliveryAddress(resultSet.getString(DELIVERY_ADDRESS));
        order.setAdditionalInfo(resultSet.getString(ADDITIONAL_INFO));
        order.setContactPhoneNo(resultSet.getString(CONTACT_PHONE_NO));
        order.setStatus(OrderStatus.valueOf(resultSet.getString(STATUS)));
        order.setOrderDate(resultSet.getTimestamp(ORDER_DATE));
        return order;
    }
}
