package com.es.core.dao.orderDao;

import com.es.core.dao.SqlQueryConstants;
import com.es.core.dao.orderDao.orderItemDao.OrderItemDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.exception.NoSuchOrderException;
import com.es.core.service.order.orderDao.OrderDaoService;
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
    @Resource
    private OrderDaoService orderDaoService;

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

    @Override
    public Optional<Order> get(String orderKey) {
        Long orderId = getOrderIdByOrderKey(orderKey).orElseThrow(NoSuchOrderException::new);
        return get(orderId);
    }

    @Override
    public Optional<String> getOrderKey(Long orderId){
        try {
            String orderKey = jdbcTemplate.queryForObject(
                    SqlQueryConstants.OrderDao.SELECT_ORDER_KEY_BY_ORDER_ID + orderId, String.class);
            return Optional.of(orderKey);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private Optional<Long> getOrderIdByOrderKey(String orderKey){
        try{
            Long orderId = jdbcTemplate.queryForObject(
                    SqlQueryConstants.OrderDao.SELECT_ORDER_ID_BY_ORDER_KEY + "\'" + orderKey + "\'", Long.class);
            return Optional.of(orderId);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private void insertOrder(Order order){
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(order);
        Long orderId = insertOrder.executeAndReturnKey(parameters).longValue();
        order.setId(orderId);
        String orderKey = orderDaoService.generateOrderKey();
        jdbcTemplate.update(SqlQueryConstants.OrderDao.INSERT_ORDER_KEY, orderId, orderKey);

        saveOrderItems(order);

    }

    private void saveOrderItems(Order order){
        jdbcTemplate.update(SqlQueryConstants.OrderDao.DELETE_ORDER_ITEMS_BELONGS_TO_ORDER + order.getId());
        orderItemDao.saveOrderItems(order.getOrderItems());
    }
}
