package com.es.core.model.order;

import com.es.core.model.mappers.OrderRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class JdbcOrderDao implements OrderDao {

    private static final String INSERT_ORDER = "INSERT INTO orders(id, subtotal, deliveryPrice," +
            " totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInformation ,status)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_BY_ID = "SELECT id, subtotal, deliveryPrice, totalPrice, firstName, " +
            "lasName, deliveryAddress, contactPhoneNo, additionalInformation, status FROM orders WHERE id=?";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private OrderRowMapper orderRowMapper;

    @Override
    public void insert(Order order) {
        jdbcTemplate.update(INSERT_ORDER, new Object[]{order.getId(), order.getSubtotal(), order.getDeliveryPrice(),
                order.getTotalPrice(), order.getFirstName(), order.getLastName(), order.getDeliveryAddress(),
                order.getContactPhoneNo(), order.getAdditionalInformation(), order.getStatus()});
    }

    @Override
    public Order loadOrderById(long orderId) {
        return jdbcTemplate.queryForObject(SELECT_ORDER_BY_ID, new Object[]{orderId}, orderRowMapper);
    }
}
