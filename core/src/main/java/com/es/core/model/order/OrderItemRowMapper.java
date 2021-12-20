package com.es.core.model.order;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    public static final String ORDER_ITEMS_ID = "ORDERITEMS.ID";
    public static final String QUANTITY = "quantity";
    public static final String PHONE_ID = "PHONEID";

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong(ORDER_ITEMS_ID));
        orderItem.setQuantity(resultSet.getLong(QUANTITY));
        orderItem.setPhoneId(resultSet.getLong(PHONE_ID));
        return orderItem;
    }
}
