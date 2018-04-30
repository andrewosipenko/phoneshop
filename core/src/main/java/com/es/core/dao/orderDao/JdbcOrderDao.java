package com.es.core.dao.orderDao;

import com.es.core.dao.SqlQueryConstants;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private PhoneDao phoneDao;
    private SimpleJdbcInsert insertOrder;
    private SimpleJdbcInsert insertOrderItem;

    @Resource
    public void setDataSource(DataSource dataSource){
        insertOrder = new SimpleJdbcInsert(dataSource).withTableName("orders").usingGeneratedKeyColumns("id");
        insertOrderItem = new SimpleJdbcInsert(dataSource).withTableName("orderItems").usingGeneratedKeyColumns("id");
    }
    @Override
    public void save(Order order) {
        insertOrder(order);
    }

    @Override
    public Optional<Order> get(Long orderId) {
        try {
            Order order = jdbcTemplate.queryForObject(SqlQueryConstants.OrderDao.SELECT_ORDER_BY_ID + orderId,
                    new BeanPropertyRowMapper<>(Order.class));
            setOrderItems(order);
            return Optional.of(order);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private void insertOrder(Order order){
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(order);
        Long orderId = insertOrder.executeAndReturnKey(parameters).longValue();
        order.setId(orderId);

        insertOrderItems(order.getOrderItems());
    }

    private void insertOrderItems(List<OrderItem> orderItems){
        orderItems.forEach(this::insertOrderItem);
    }

    private void insertOrderItem(OrderItem orderItem){
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(orderItem);
        Long orderItemId = insertOrderItem.executeAndReturnKey(sqlParameterSource).longValue();
        orderItem.setId(orderItemId);

        jdbcTemplate.update(SqlQueryConstants.OrderDao.SET_PHONE_ID_IN_ORDER_ITEM,
                orderItem.getPhone().getId(), orderItemId);
        jdbcTemplate.update(SqlQueryConstants.OrderDao.SET_ORDER_ID_IN_ORDER_ITEM, orderItem.getOrder().getId(), orderItem.getId());
        jdbcTemplate.update(SqlQueryConstants.OrderDao.INSERT_ORDER_ITEM_BELONG_TO_ORDER, orderItem.getOrder().getId(), orderItem.getId());
    }

    private void setOrderItems(Order order){
        List<OrderItem> orderItems = jdbcTemplate.
                query(SqlQueryConstants.OrderDao.SELECT_ORDER_ITEMS_BELONG_TO_ORDER + order.getId(), new BeanPropertyRowMapper<>(OrderItem.class));
        orderItems.forEach(this::setPhone);
        order.setOrderItems(orderItems);
    }

    private void setPhone(OrderItem orderItem){
        Long phoneId = jdbcTemplate.
                queryForObject(SqlQueryConstants.OrderDao.
                        SELECT_PHONE_ID_BELONG_TO_ORDER_ITEM + orderItem.getId(), Long.class);
        Phone phone = phoneDao.get(phoneId).get();
        orderItem.setPhone(phone);
    }
}
