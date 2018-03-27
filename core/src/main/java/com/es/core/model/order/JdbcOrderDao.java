package com.es.core.model.order;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toMap;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_ORDERS = "SELECT id, firstName, lastName, deliveryAddress, contactPhoneNo, " +
                                                    "additionInfo, date, subtotal, deliveryPrice, total, status, phoneId, quantity " +
                                                    "FROM orders JOIN order2phone ON orders.id = order2phone.orderId";

    private static final String SELECT_ORDER = SELECT_ALL_ORDERS + " WHERE id = ?";

    private static final String SELECT_COUNT_PRODUCT_IN_STOCK = "SELECT stock FROM stocks WHERE phoneId = ?";

    private static final String INSERT_PHONE_AND_QUANTITY = "INSERT INTO order2phone (orderId, phoneId, quantity) " +
                                                            "VALUES (?, ?, ?)";

    private static final String UPDATE_DECREASE_PRODUCT_STOCK = "UPDATE stocks SET stock = ? WHERE phoneId = ?";

    private static final String UPDATE_CHANGE_ORDER_STATUS = "UPDATE orders SET status = ? WHERE id = ?";

    @Override
    public Optional<Order> get(long id) {
        List<Order> orders = jdbcTemplate.query(SELECT_ORDER, new OrderExtractor(), id);
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(orders.get(0));
    }

    @Override
    public List<Order> getOrders() {
        return jdbcTemplate.query(SELECT_ALL_ORDERS, new OrderExtractor());
    }

    @Override
    public void save(Order order) {
        long orderId = saveOrderAndReturnId(order);
        order.setId(orderId);
        saveOrderedPhones(order);
    }

    @Override
    public void decreaseProductStock(Order order) {
        Map<Long, Long> items = order.getOrderItems().stream()
                .collect(toMap(OrderItem::getPhoneId, OrderItem::getQuantity));
        items.keySet().forEach(phoneId -> {
            long stockCount = jdbcTemplate.queryForObject(SELECT_COUNT_PRODUCT_IN_STOCK, Long.class, phoneId);
            jdbcTemplate.update(UPDATE_DECREASE_PRODUCT_STOCK, stockCount - items.get(phoneId), phoneId);
        });
    }

    @Override
    public void changeOrderStatus(long id, OrderStatus orderStatus) {
        jdbcTemplate.update(UPDATE_CHANGE_ORDER_STATUS, orderStatus.name(), id);
    }

    private long saveOrderAndReturnId(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", order.getFirstName());
        params.put("lastName", order.getLastName());
        params.put("deliveryAddress", order.getDeliveryAddress());
        params.put("contactPhoneNo", order.getContactPhoneNo());
        params.put("additionInfo", order.getAdditionInfo());
        params.put("subtotal", order.getSubtotal());
        params.put("deliveryPrice", order.getDeliveryPrice());
        params.put("total", order.getTotalPrice());
        params.put("status", order.getStatus().name());
        params.put("date", new Timestamp(System.currentTimeMillis()));

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("orders");
        jdbcInsert.usingGeneratedKeyColumns("id");
        return (long) jdbcInsert.executeAndReturnKey(params);
    }

    private void saveOrderedPhones(Order order) {
        Map<Long, Long> items = order.getOrderItems().stream()
                .collect(toMap(OrderItem::getPhoneId, OrderItem::getQuantity));
        items.keySet().forEach(phoneId -> jdbcTemplate.update(INSERT_PHONE_AND_QUANTITY,
                                                              order.getId(),
                                                              phoneId,
                                                              items.get(phoneId)));
    }

    private static class OrderExtractor implements ResultSetExtractor<List<Order>> {
        @Override
        public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Order> orders = new HashMap<>();
            List<Order> ordersList = new ArrayList<>();
            while (rs.next()) {
                Order order;
                long orderId = rs.getLong("id");
                if (!orders.containsKey(orderId)) {
                    order = getOrderWithProperties(rs);
                    orders.put(orderId, order);
                    ordersList.add(order);
                } else {
                    order = orders.get(orderId);
                }
                addOrderItem(order, rs);
            }
            return ordersList;
        }

        private Order getOrderWithProperties(ResultSet rs) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setFirstName(rs.getString("firstName"));
            order.setLastName(rs.getString("lastName"));
            order.setDeliveryAddress(rs.getString("deliveryAddress"));
            order.setContactPhoneNo(rs.getString("contactPhoneNo"));
            order.setAdditionInfo(rs.getString("additionInfo"));
            order.setDate(rs.getTimestamp("date"));
            order.setSubtotal(rs.getBigDecimal("subtotal"));
            order.setDeliveryPrice(rs.getBigDecimal("deliveryPrice"));
            order.setTotalPrice(rs.getBigDecimal("total"));
            order.setStatus(OrderStatus.valueOf(rs.getString("status")));
            order.setOrderItems(new ArrayList<>());
            return order;
        }

        private void addOrderItem(Order order, ResultSet rs) throws SQLException {
            List<OrderItem> orderItems = order.getOrderItems();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPhoneId(rs.getLong("phoneId"));
            orderItem.setQuantity(rs.getLong("quantity"));
            orderItems.add(orderItem);
            order.setOrderItems(orderItems);
        }
    }
}
