package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import com.es.core.order.OutOfStockException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_COUNT_PRODUCT_IN_STOCK = "SELECT stock FROM stocks WHERE phoneId = ?";

    private static final String INSERT_PHONE_AND_QUANTITY = "INSERT INTO order2phone (orderId, phoneId, quantity) " +
                                                            "VALUES (?, ?, ?)";

    private static final String UPDATE_DECREASE_PRODUCT_STOCK = "UPDATE stocks SET stock = ? WHERE phoneId = ?";

    @Override
    public void save(Order order) throws OutOfStockException {
        checkItemsInOrder(order);
        long orderId = saveOrderAndReturnId(order);
        order.setId(orderId);
        decreaseProductStock(order);
        saveOrderedPhones(order);
    }

    private void checkItemsInOrder(Order order) throws OutOfStockException {
        Map<Phone, Long> items = order.getOrderItems().stream()
                .collect(toMap(OrderItem::getPhone, OrderItem::getQuantity));
        for (Phone phone : items.keySet()) {
            long stockCount = jdbcTemplate.queryForObject(SELECT_COUNT_PRODUCT_IN_STOCK, Long.class, phone.getId());
            if (stockCount < items.get(phone)) {
                throw new OutOfStockException();
            }
        }
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

    private void decreaseProductStock(Order order) {
        Map<Phone, Long> items = order.getOrderItems().stream()
                .collect(toMap(OrderItem::getPhone, OrderItem::getQuantity));
        items.keySet().forEach(phone -> {
            long stockCount = jdbcTemplate.queryForObject(SELECT_COUNT_PRODUCT_IN_STOCK, Long.class, phone.getId());
            jdbcTemplate.update(UPDATE_DECREASE_PRODUCT_STOCK, stockCount - items.get(phone), phone.getId());
        });
    }

    private void saveOrderedPhones(Order order) {
        Map<Phone, Long> items = order.getOrderItems().stream()
                .collect(toMap(OrderItem::getPhone, OrderItem::getQuantity));
        items.keySet().forEach(phone -> jdbcTemplate.update(INSERT_PHONE_AND_QUANTITY,
                                                            order.getId(),
                                                            phone.getId(),
                                                            items.get(phone)));
    }
}
