package com.es.phoneshop.core.order.dao;

import com.es.phoneshop.core.order.model.OrderItem;
import com.es.phoneshop.core.phone.service.PhoneService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Resource
    private PhoneService phoneService;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem item = new OrderItem();
        item.setQuantity(resultSet.getLong("quantity"));
        item.setPhone(phoneService.getPhone(resultSet.getLong("phoneId")).get());
        return item;
    }
}
