package com.es.core.dao.orderDao;

import com.es.core.dao.SqlQueryConstants;
import com.es.core.dao.orderDao.orderItemDao.OrderItemDao;
import com.es.core.model.order.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Optional;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private OrderItemDao orderItemDao;

    private SimpleJdbcInsert insertOrder;

    @Resource
    public void setDataSource(DataSource dataSource){
        insertOrder = new SimpleJdbcInsert(dataSource).withTableName("orders").usingGeneratedKeyColumns("id");
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
            order.setOrderItems(orderItemDao.getOrderItemsByOrderId(order.getId()));
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

        saveOrderItems(order);

    }

    private void saveOrderItems(Order order){
        jdbcTemplate.update(SqlQueryConstants.OrderDao.DELETE_ORDER_ITEMS_BELONGS_TO_ORDER + order.getId());
        orderItemDao.saveOrderItems(order.getOrderItems());
    }
}
