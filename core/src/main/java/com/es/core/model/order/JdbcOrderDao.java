package com.es.core.model.order;

import com.es.core.model.mappers.OrderRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderDao implements OrderDao {

    private static final String INSERT_ORDER = "INSERT INTO orders(id, subtotal, dateTime, deliveryPrice," +
            " totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInformation ,status)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_BY_ID = "SELECT id, subtotal, dateTime, deliveryPrice, totalPrice, firstName, " +
            "lastName, deliveryAddress, contactPhoneNo, additionalInformation, status FROM orders WHERE id=?";

    private static final String SELECT_LAST_ID = "SELECT MAX(id) from orders";

    private static final String SELECT_ALL_ORDERS = "SELECT id, subtotal, dateTime, deliveryPrice, totalPrice, firstName, " +
            "lastName, deliveryAddress, contactPhoneNo, additionalInformation, status FROM orders";

    private static final String UPDATE_STATUS = "UPDATE orders SET status=? WHERE id=?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private OrderRowMapper orderRowMapper;

    @Override
    public void insert(Order order) {
        jdbcTemplate.update(INSERT_ORDER, order.getId(), order.getSubtotal(), order.getLocalDateTime().toString(), order.getDeliveryPrice(),
                order.getTotalPrice(), order.getFirstName(), order.getLastName(), order.getDeliveryAddress(),
                order.getContactPhoneNo(), order.getAdditionalInformation(), order.getStatus().toString());
    }

    @Override
    public Order loadOrderById(long orderId) {
        return jdbcTemplate.queryForObject(SELECT_ORDER_BY_ID, new Object[]{orderId}, orderRowMapper);
    }

    @Override
    public Optional<Long> getLastInsertedId() {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_LAST_ID, Long.class));
    }

    @Override
    public List<Order> loadAllOrders() {
        return jdbcTemplate.query(SELECT_ALL_ORDERS, orderRowMapper);
    }

    @Override
    public void updateOrderStatus(long orderId, OrderStatus orderStatus) {
        jdbcTemplate.update(UPDATE_STATUS, orderStatus.toString(), orderId);
    }
}
