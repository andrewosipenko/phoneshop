package com.es.core.dao.orderDao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class OrderDaoImpl implements OrderDao {

    public static final String SELECT_FROM_ORDERS_WHERE_ID = "SELECT * FROM orders WHERE id = ?";
    public static final String SELECT_FROM_ITEM_2_ORDER_WHERE_ORDER_ID = "SELECT * FROM item2order WHERE orderId = ?";
    public static final String UPDATE_ORDERS_SET_STATUS_WHERE_ID = "UPDATE orders SET status = ? WHERE id = ?";
    public static final String SELECT_FROM_ORDERS = "SELECT * FROM orders";
    public static final String ORDER_ID = "orderId";
    public static final String PHONE_ID = "phoneId";
    public static final String QUANTITY = "quantity";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SimpleJdbcInsert orderSimpleJdbcInsert;

    @Resource
    private SimpleJdbcInsert orderItemSimpleJdbcInsert;

    @Resource
    private BeanPropertyRowMapper<Order> orderBeanPropertyRowMapper;

    @Resource
    private OrderItemRowMapper orderItemRowMapper;


    @Override
    public Optional<Order> get(Long key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        Order order;
        try {
            order = jdbcTemplate.queryForObject(SELECT_FROM_ORDERS_WHERE_ID, orderBeanPropertyRowMapper, key);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
        order.setOrderItems(extractOrderItems(order));
        return Optional.of(order);
    }

    @Override
    public Long save(Order order) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        Number orderId = orderSimpleJdbcInsert.executeAndReturnKey(parameterSource);
        order.setId(orderId.longValue());
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(this::save);
        return (Long) orderId;
    }

    private void save(OrderItem orderItem) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue(ORDER_ID, orderItem.getOrder().getId());
        params.addValue(PHONE_ID, orderItem.getPhone().getId());
        params.addValue(QUANTITY, orderItem.getQuantity());

        orderItemSimpleJdbcInsert.execute(params);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = jdbcTemplate.query(SELECT_FROM_ORDERS, orderBeanPropertyRowMapper);

        orders.forEach(order -> order.setOrderItems(extractOrderItems(order)));

        return orders;
    }

    private List<OrderItem> extractOrderItems(Order order) {
        List<OrderItem> orderItems = jdbcTemplate.query(SELECT_FROM_ITEM_2_ORDER_WHERE_ORDER_ID, orderItemRowMapper, order.getId());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        return orderItems;
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        jdbcTemplate.update(UPDATE_ORDERS_SET_STATUS_WHERE_ID, orderStatus.name(), id);
    }
}
