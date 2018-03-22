package com.es.core.dao.order;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional(rollbackFor = OutOfStockException.class)
public class JdbcOrderDao implements OrderDao {

    private final static String SELECT_ORDER_QUERY = "select orders.id AS orderId, subtotal, deliveryPrice," +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, status," +
            "additionalInfo, orderItems.id AS orderItemId, quantity," +
            "phones.id AS phoneId, brand, model, price, displaySizeInches, weightGr, lengthMm," +
            "widthMm, heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from orders " +
            "left join orderItems on orders.id = orderItems.orderId " +
            "left join phones on phones.id = orderItems.phoneId " +
            "left join phone2color on phones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId " +
            "where orders.id = ?";

    private final static String SELECT_ORDER_LIST_QUERY = "select limitedOrders.id AS orderId, subtotal," +
            "deliveryPrice, totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, status," +
            "additionalInfo, orderItems.id AS orderItemId, quantity," +
            "phones.id AS phoneId, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from " +
            "(select * from orders order by id desc offset ? limit ?) as limitedOrders " +
            "left join orderItems on limitedOrders.id = orderItems.orderId " +
            "left join phones on phones.id = orderItems.phoneId " +
            "left join phone2color on phones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId ";

    private final static String ORDER_COUNT_QUERY = "select count(1) from orders";

    private final static String UPDATE_ORDER_QUERY = "update orders set subtotal = ? ,deliveryPrice = ? ," +
            "totalPrice = ? ,firstName = ? ,lastName = ? ,deliveryAddress = ? ,contactPhoneNo = ? ," +
            "additionalInfo = ? ,status = ? where id = ? ";

    private final static String DELETE_ORDER_ITEM_QUERY = "delete from orderItems where id = ?";

    private final static String UPDATE_ORDER_ITEM_QUERY = "update orderItems set orderId = ?, phoneId = ?, quantity = ?" +
            "where id = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertOrder;
    private SimpleJdbcInsert insertOrderItems;

    @PostConstruct
    public void initSimpleJdbcInsert() {
        this.insertOrder = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");

        this.insertOrderItems = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orderItems")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Order> get(Long key) {
        List<Order> orderList = jdbcTemplate.query(SELECT_ORDER_QUERY, OrderListResultSetExtractor.getInstanse(), key);
        if (orderList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(orderList.get(0));
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        return jdbcTemplate.query(SELECT_ORDER_LIST_QUERY, OrderListResultSetExtractor.getInstanse(), offset, limit);
    }

    @Override
    public int orderCount() {
        return jdbcTemplate.queryForObject(ORDER_COUNT_QUERY, Integer.class);
    }

    @Override
    public void save(Order order) throws OutOfStockException {
        if (order.getId() == null) {
            insert(order);
        } else {
            update(order);
        }
    }

    private void insert(Order order) throws OutOfStockException {
        SqlParameterSource parameters = getParameters(order);
        final long newId = insertOrder.executeAndReturnKey(parameters).longValue();
        order.setId(newId);
        insertOrderItems(order.getOrderItems());
    }

    private void update(Order order) throws OutOfStockException {
        jdbcTemplate.update(UPDATE_ORDER_QUERY,
                order.getSubtotal(), order.getDeliveryPrice(),
                order.getTotalPrice(), order.getFirstName(),
                order.getLastName(), order.getDeliveryAddress(),
                order.getContactPhoneNo(), order.getAdditionalInfo(),
                order.getStatus().name(), order.getId());

        saveOrderItems(order);
    }

    private void saveOrderItems(Order order) throws OutOfStockException {
        Optional<Order> previousOrderOptional = get(order.getId());
        if (previousOrderOptional.isPresent()) {
            Order previousOrder = previousOrderOptional.get();

            List<OrderItem> deletedOrderItems = previousOrder.getOrderItems();
            deletedOrderItems.removeAll(order.getOrderItems());
            deleteOrderItems(deletedOrderItems);

            List<OrderItem> updatedOrderItems = order.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getId() != null)
                    .collect(Collectors.toList());
            updateOrderItems(updatedOrderItems);

            List<OrderItem> insertedOrderItems = order.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getId() == null)
                    .collect(Collectors.toList());
            insertOrderItems(insertedOrderItems);
        }
    }

    private void insertOrderItems(List<OrderItem> orderItems) throws OutOfStockException {
        try {
            for (OrderItem orderItem : orderItems) {
                SqlParameterSource parameters = getParameters(orderItem);
                long newId = insertOrderItems.executeAndReturnKey(parameters).longValue();
                orderItem.setId(newId);
            }
        } catch (DataAccessException e) {
            throw new OutOfStockException(e);
        }
    }

    private SqlParameterSource getParameters(Order order) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("subtotal", order.getSubtotal());
        mapSqlParameterSource.addValue("deliveryPrice", order.getDeliveryPrice());
        mapSqlParameterSource.addValue("totalPrice", order.getTotalPrice());
        mapSqlParameterSource.addValue("firstName", order.getFirstName());
        mapSqlParameterSource.addValue("lastName", order.getLastName());
        mapSqlParameterSource.addValue("deliveryAddress", order.getDeliveryAddress());
        mapSqlParameterSource.addValue("contactPhoneNo", order.getContactPhoneNo());
        mapSqlParameterSource.addValue("additionalInfo", order.getAdditionalInfo());
        mapSqlParameterSource.addValue("status", order.getStatus().name(), 12);
        return mapSqlParameterSource;
    }

    private SqlParameterSource getParameters(OrderItem orderItem) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", orderItem.getId());
        mapSqlParameterSource.addValue("phoneId", orderItem.getPhone().getId());
        mapSqlParameterSource.addValue("orderId", orderItem.getOrder().getId());
        mapSqlParameterSource.addValue("quantity", orderItem.getQuantity());
        return mapSqlParameterSource;
    }

    private void updateOrderItems(List<OrderItem> orderItems) throws OutOfStockException {
        try {
            jdbcTemplate.batchUpdate(UPDATE_ORDER_ITEM_QUERY,
                    new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            OrderItem orderItem = orderItems.get(i);
                            ps.setLong(1, orderItem.getOrder().getId());
                            ps.setLong(2, orderItem.getPhone().getId());
                            ps.setLong(3, orderItem.getQuantity());
                            ps.setLong(4, orderItem.getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return orderItems.size();
                        }
                    });
        } catch (DataAccessException e) {
            throw new OutOfStockException(e);
        }
    }

    private void deleteOrderItems(List<OrderItem> orderItems) throws OutOfStockException {
        jdbcTemplate.batchUpdate(DELETE_ORDER_ITEM_QUERY,
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        OrderItem orderItem = orderItems.get(i);
                        ps.setLong(1, orderItem.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return orderItems.size();
                    }
                });
    }
}
