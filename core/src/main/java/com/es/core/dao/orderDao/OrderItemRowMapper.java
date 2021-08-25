package com.es.core.dao.orderDao;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.exceptions.ProductNotFoundException;
import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {

    @Resource
    private PhoneDao phoneDao;

    public OrderItem mapRow(ResultSet resultSet, int i) {
        try {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(resultSet.getLong("id"));
            orderItem.setPhone(
                    phoneDao.get(resultSet
                                    .getLong("phoneId"))
                            .orElseThrow(ProductNotFoundException::new)
            );
            orderItem.setQuantity(resultSet.getLong("quantity"));
            return orderItem;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
