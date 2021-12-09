package com.es.core.model.order;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong("ORDERITEMS.ID"));
        orderItem.setQuantity(resultSet.getLong("quantity"));
        orderItem.setPhoneId(resultSet.getLong("PHONEID"));
        return orderItem;
    }
}
