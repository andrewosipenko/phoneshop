package com.es.core.dao.orderDao;

import com.es.core.model.order.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public void save(Order order) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Order get(Long orderId) {
        throw new UnsupportedOperationException("TODO");
    }
}
