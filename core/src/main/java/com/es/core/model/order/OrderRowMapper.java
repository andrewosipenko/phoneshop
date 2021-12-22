package com.es.core.model.order;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class OrderRowMapper implements RowMapper<Order> {

    public static final String ID = "id";
    public static final String SUBTOTAL = "subtotal";
    public static final String DELIVERY_PRICE = "deliveryPrice";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String DELIVERY_ADDRESS = "deliveryAddress";
    public static final String CONTACT_PHONE_NO = "contactPhoneNo";
    public static final String STATUS = "status";
    public static final String ADDITIONAL_INFO = "additionalInfo";
    public static final String SECURE_ID = "secureId";
    public static final String ORDER_DATE = "orderDate";

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong(ID));
        order.setSubtotal(resultSet.getBigDecimal(SUBTOTAL));
        order.setDeliveryPrice(resultSet.getBigDecimal(DELIVERY_PRICE));
        order.setTotalPrice(resultSet.getBigDecimal(TOTAL_PRICE));
        order.setFirstName(resultSet.getString(FIRST_NAME));
        order.setLastName(resultSet.getString(LAST_NAME));
        order.setDeliveryAddress(resultSet.getString(DELIVERY_ADDRESS));
        order.setContactPhoneNo(resultSet.getString(CONTACT_PHONE_NO));
        order.setStatus(OrderStatus.valueOf(resultSet.getString(STATUS)));
        order.setAdditionalInfo(resultSet.getString(ADDITIONAL_INFO));
        order.setSecureId(UUID.fromString(resultSet.getString(SECURE_ID)));
        order.setDate(LocalDate.parse(resultSet.getString(ORDER_DATE)));
        return order;
    }
}
