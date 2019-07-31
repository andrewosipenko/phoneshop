package com.es.core.model.mappers;

import com.es.core.model.ProductDao;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {

    @Resource
    private ProductDao productDao;

    @Resource
    private OrderDao orderDao;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong("id"));
        orderItem.setPhone(productDao.loadPhoneById(resultSet.getLong("phoneId")));
        orderItem.setQuantity(resultSet.getLong("quantity"));
        return orderItem;
    }
}
