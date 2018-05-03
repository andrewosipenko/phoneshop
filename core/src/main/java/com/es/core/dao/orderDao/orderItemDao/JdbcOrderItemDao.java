package com.es.core.dao.orderDao.orderItemDao;

import com.es.core.dao.SqlQueryConstants;
import com.es.core.dao.orderDao.OrderDao;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.exception.NoSuchPhoneException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcOrderItemDao implements OrderItemDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertOrderItem;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    public void setDataSource(DataSource dataSource){
        insertOrderItem = new SimpleJdbcInsert(dataSource).withTableName("orderItems").usingGeneratedKeyColumns("id");
    }

    @Override
    public void saveOrderItems(List<OrderItem> orderItems){
        orderItems.forEach(this::insertOrderItem);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId){
        List<OrderItem> orderItems = jdbcTemplate.
                query(SqlQueryConstants.OrderDao.SELECT_ORDER_ITEMS_BELONG_TO_ORDER + orderId,
                        new BeanPropertyRowMapper<>(OrderItem.class));
        orderItems.forEach(this::setPhone);
        return orderItems;
    }

    private void insertOrderItem(OrderItem orderItem){
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(orderItem);
        Long orderItemId = insertOrderItem.executeAndReturnKey(sqlParameterSource).longValue();
        orderItem.setId(orderItemId);

        updateDependencies(orderItem);
    }

    private void setPhone(OrderItem orderItem){
        Long phoneId = jdbcTemplate.
                queryForObject(SqlQueryConstants.OrderDao.
                        SELECT_PHONE_ID_BELONG_TO_ORDER_ITEM + orderItem.getId(), Long.class);
        Phone phone = phoneDao.get(phoneId).orElseThrow(NoSuchPhoneException::new);
        orderItem.setPhone(phone);
    }

    private void updateDependencies(OrderItem orderItem){
        jdbcTemplate.update(SqlQueryConstants.OrderDao.INSERT_ORDER_ITEM_BELONG_TO_ORDER,
                orderItem.getOrder().getId(), orderItem.getId());

        jdbcTemplate.update(SqlQueryConstants.OrderDao.SET_PHONE_ID_IN_ORDER_ITEM,
                orderItem.getPhone().getId(), orderItem.getId());

        jdbcTemplate.update(SqlQueryConstants.OrderDao.SET_ORDER_ID_IN_ORDER_ITEM,
                orderItem.getOrder().getId(), orderItem.getId());
    }
}
