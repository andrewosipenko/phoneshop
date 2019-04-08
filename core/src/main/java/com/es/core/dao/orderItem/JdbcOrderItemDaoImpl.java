package com.es.core.dao.orderItem;

import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JdbcOrderItemDaoImpl implements OrderItemDao {
    private static final String QUERY_TO_SAVE_ORDER_ITEMS =
            "insert into orderItems (orderId, phoneId, quantity)" +
                    " values (?, ?, ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(OrderItem orderItem, Long orderId) {
        jdbcTemplate.update(QUERY_TO_SAVE_ORDER_ITEMS, orderId, orderItem.getPhone().getId(), orderItem.getQuantity());
    }
}
