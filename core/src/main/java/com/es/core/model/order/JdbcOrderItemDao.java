package com.es.core.model.order;

import com.es.core.model.mappers.OrderItemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JdbcOrderItemDao implements OrderItemDao {

    private static final String INSERT_ORDER_ITEM = "INSERT INTO order_items(id, phoneId, orderId, quantity) " +
            "VALUES(?, ?, ?, ?)";

    private static final String SELECT_ORDER_ITEM_BY_ID = "SELECT id, phoneId, orderId, quantity FROM order_items WHERE id=?";

    private static final String SELECT_ORDER_ITEM_BY_ORDER_ID = "SELECT id, phoneId, orderId, quantity FROM order_items WHERE orderId=?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private OrderItemRowMapper orderItemRowMapper;

    @Override
    public void insert(OrderItem orderItem) {
        jdbcTemplate.update(INSERT_ORDER_ITEM, new Object[]{orderItem.getId(),
                orderItem.getPhone().getId(), orderItem.getOrder().getId(), orderItem.getQuantity()});
    }

    @Override
    public OrderItem loadOrderItemById(long orderItemId) {
        return jdbcTemplate.queryForObject(SELECT_ORDER_ITEM_BY_ID, new Object[]{orderItemId}, orderItemRowMapper);
    }

    @Override
    public List<OrderItem> loadOrderItemsOfOrderByOrderId(long orderId) {
        return jdbcTemplate.query(SELECT_ORDER_ITEM_BY_ORDER_ID, new Object[]{orderId}, orderItemRowMapper);
    }
}
