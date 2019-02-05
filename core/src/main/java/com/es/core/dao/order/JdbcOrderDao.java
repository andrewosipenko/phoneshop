package com.es.core.dao.order;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.exceptions.order.OrderException;
import com.es.core.exceptions.phone.PhoneException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.service.key.KeyService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcOrderDao implements OrderDao {
    private final String SQL_SELECT_ALL_ORDERS = "select * from orders ";
    private final String SQL_SELECT_ORDER_BY_ID = SQL_SELECT_ALL_ORDERS + "where id = :id";
    private final String SQL_SELECT_ORDER_KEY_BY_ORDER_ID = "select orderKey from orderId2OrderKey where orderId = :orderId";
    private final String SQL_SELECT_ORDER_ID_BY_ORDER_KEY = "select orderId from orderId2OrderKey where orderKey = :orderKey";
    private final String SQL_UPDATE_STATUS_BY_ID = "update orders set status = :status where id = :id";
    private final String SQL_SELECT_ORDERS_ITEMS_BY_ID = "select * from orderItems inner join order2orderItem" +
            " on orderItems.id = order2orderItem.orderItemId where order2orderItem.orderId = :orderId";
    private final String SQL_SELECT_PHONE_ID_BELONG_TO_ORDER_ITEM = "select phoneId from orderItems where orderItems.id = :id";
    private final String SQL_INSERT_ORDER_ITEM_BELONG_TO_ORDER = "insert into order2orderItem (orderId, orderItemId) values(:orderId, :orderItemId)";
    private final String SQL_UPDATE_PHONE_ID = "update orderItems set phoneId = :phoneId where id = :id";
    private final String SQL_UPDATE_ORDER_ID = "update orderItems set orderId = :orderId where id = :id";
    private final String SQL_INSERT_ORDER_KEY = "insert into orderId2orderKey values(:orderId, :orderKey)";
    private final String SQL_DELETE_ORDER_ITEMS = "delete from orderItems where orderId = ";
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SqlParameterSource sqlParameterSource;
    private BeanPropertyRowMapper<Order> orderBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Order.class);
    private BeanPropertyRowMapper<OrderItem> orderItemBeanPropertyRowMapper = new BeanPropertyRowMapper<>(OrderItem.class);

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private KeyService keyService;

    @Override
    public Optional<Order> get(Long orderId) {
        try {
            this.sqlParameterSource = new MapSqlParameterSource("id", orderId);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            Order order = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_ORDER_BY_ID, sqlParameterSource, orderBeanPropertyRowMapper);
            order.setOrderItems(getOrderItems(orderId));
            return Optional.of(order);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> get(String key) {
        Long orderId = getByKey(key).orElseThrow(OrderException::new);
        return get(orderId);
    }

    @Override
    public Optional<Long> getByKey(String key) {
        try {
            this.sqlParameterSource = new MapSqlParameterSource("orderKey", key);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            Long orderId = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_ORDER_ID_BY_ORDER_KEY, sqlParameterSource, Long.class);
            return Optional.of(orderId);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Order order) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("orders").usingGeneratedKeyColumns("id");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(order);
        Long orderId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        order.setId(orderId);
        String orderKey = keyService.generateOrderKey();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.namedParameterJdbcTemplate.update(SQL_INSERT_ORDER_KEY, getAllValuesOrderId2OrderKey(orderId, orderKey));
        this.jdbcTemplate.update(SQL_DELETE_ORDER_ITEMS + orderId);
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
        }
        saveOrderItems(order.getOrderItems());
    }

    private Map<String, Object> getAllValuesOrderId2OrderKey(Long orderId, String orderKey) {
        Map<String, Object> allValuesOrderId2OrderKey = new HashMap<>();
        allValuesOrderId2OrderKey.put("orderId", orderId);
        allValuesOrderId2OrderKey.put("orderKey", orderKey);
        return allValuesOrderId2OrderKey;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = this.jdbcTemplate.query(SQL_SELECT_ALL_ORDERS, orderBeanPropertyRowMapper);
        for (Order order : orders) {
            List<OrderItem> orderItems = getOrderItems(order.getId());
            order.setOrderItems(orderItems);
        }
        return orders;
    }

    @Override
    public Optional<String> getOrderKey(Long orderId) {
        try {
            this.sqlParameterSource = new MapSqlParameterSource("orderId", orderId);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            String orderKey = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_ORDER_KEY_BY_ORDER_ID, sqlParameterSource, String.class);
            return Optional.of(orderKey);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.namedParameterJdbcTemplate.update(SQL_UPDATE_STATUS_BY_ID, getAllValuesOrderStatusOrder(orderStatus.toString(), orderId));
    }

    @Override
    public void saveOrderItems(List<OrderItem> orderItems) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("orderItems").usingGeneratedKeyColumns("id");
        for (OrderItem orderItem : orderItems) {
            SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(orderItem);
            Long orderItemId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
            orderItem.setId(orderItemId);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            this.namedParameterJdbcTemplate.update(SQL_INSERT_ORDER_ITEM_BELONG_TO_ORDER, getAllValuesOrder2OrderItem(orderItem.getOrder().getId(), orderItem.getId()));
            this.namedParameterJdbcTemplate.update(SQL_UPDATE_PHONE_ID, getAllValuesOrderItemsPhone(orderItem.getPhone().getId(), orderItem.getId()));
            this.namedParameterJdbcTemplate.update(SQL_UPDATE_ORDER_ID, getAllValuesOrderItemsOrder(orderItem.getOrder().getId(), orderItem.getId()));
        }
    }

    private Map<String, Object> getAllValuesOrder2OrderItem(Long orderId, Long orderItemId) {
        Map<String, Object> getAllValuesOrder2OrderItem = new HashMap<>();
        getAllValuesOrder2OrderItem.put("orderId", orderId);
        getAllValuesOrder2OrderItem.put("orderItemId", orderItemId);
        return getAllValuesOrder2OrderItem;
    }

    private Map<String, Object> getAllValuesOrderItemsPhone(Long phoneId, Long id) {
        Map<String, Object> getAllValuesOrderItemsPhone = new HashMap<>();
        getAllValuesOrderItemsPhone.put("phoneId", phoneId);
        getAllValuesOrderItemsPhone.put("id", id);
        return getAllValuesOrderItemsPhone;
    }

    private Map<String, Object> getAllValuesOrderItemsOrder(Long orderId, Long id) {
        Map<String, Object> getAllValuesOrderItemsPhone = new HashMap<>();
        getAllValuesOrderItemsPhone.put("orderId", orderId);
        getAllValuesOrderItemsPhone.put("id", id);
        return getAllValuesOrderItemsPhone;
    }

    private Map<String, Object> getAllValuesOrderStatusOrder(String orderStatus, Long orderId){
        Map<String, Object> getAllValuesOrderStatusOrder = new HashMap<>();
        getAllValuesOrderStatusOrder.put("status", orderStatus);
        getAllValuesOrderStatusOrder.put("id", orderId);
        return getAllValuesOrderStatusOrder;
    }

    @Override

    public List<OrderItem> getOrderItems(Long orderId) {
        this.sqlParameterSource = new MapSqlParameterSource("orderId", orderId);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<OrderItem> orderItems = this.namedParameterJdbcTemplate.query(SQL_SELECT_ORDERS_ITEMS_BY_ID, sqlParameterSource, orderItemBeanPropertyRowMapper);
        for (OrderItem orderItem : orderItems) {
            Phone phone = getPhone(orderItem);
            orderItem.setPhone(phone);
        }
        return orderItems;
    }

    private Phone getPhone(OrderItem orderItem) {
        this.sqlParameterSource = new MapSqlParameterSource("id", orderItem.getId());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Long phoneId = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_PHONE_ID_BELONG_TO_ORDER_ITEM, sqlParameterSource, Long.class);
        Phone phone = phoneDao.get(phoneId).orElseThrow(PhoneException::new);
        return phone;
    }
}
