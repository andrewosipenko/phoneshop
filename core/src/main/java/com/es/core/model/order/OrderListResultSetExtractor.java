package com.es.core.model.order;


import com.es.core.model.AbstractPhoneResultSetExctractor;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListResultSetExtractor extends AbstractPhoneResultSetExctractor implements ResultSetExtractor<List<Order>> {

    private static OrderListResultSetExtractor instanse = new OrderListResultSetExtractor();

    public static OrderListResultSetExtractor getInstanse() {
        return instanse;
    }

    private OrderListResultSetExtractor() {
    }

    @Override
    public List<Order> extractData(ResultSet rs) throws SQLException {
        List<Order> resultList = new ArrayList<>();
        Map<Long, Order> orderMap = new HashMap<>();
        Map<Long, OrderItem> orderItemMap = new HashMap<>();

        while (rs.next()) {
            Long orderId = rs.getLong("orderId");
            Order changeOrder = orderMap.get(orderId);
            if (changeOrder == null) {
                changeOrder = readPropertiesToOrder(rs);
                resultList.add(changeOrder);
                orderMap.put(orderId, changeOrder);
            }
            addOrderItem(changeOrder, orderItemMap, rs);
        }
        return resultList;
    }

    private Order readPropertiesToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();

        order.setId(rs.getLong("orderId"));
        order.setOrderItems(new ArrayList<>());
        order.setSubtotal(rs.getBigDecimal("subtotal"));
        order.setDeliveryPrice(rs.getBigDecimal("deliveryPrice"));
        order.setTotalPrice(rs.getBigDecimal("totalPrice"));
        order.setFirstName(rs.getString("firstName"));
        order.setLastName(rs.getString("lastName"));
        order.setDeliveryAddress(rs.getString("deliveryAddress"));
        order.setContactPhoneNo(rs.getString("contactPhoneNo"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        return order;
    }

    private void addOrderItem(Order order, Map<Long, OrderItem> orderItemMap, ResultSet rs) throws SQLException {
        Long orderItemId = rs.getLong("orderItemId");
        OrderItem orderItem = orderItemMap.get(orderItemId);
        if (orderItem == null) {
            orderItem = readPropertiesToOrderItem(order, rs);
            order.getOrderItems().add(orderItem);
            orderItemMap.put(orderItemId, orderItem);
        }
        addColor(orderItem.getPhone(), rs);
    }

    private OrderItem readPropertiesToOrderItem(Order order, ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("orderItemId"));
        orderItem.setOrder(order);
        Phone phone = readPropertiesToPhone(rs);
        orderItem.setPhone(phone);
        orderItem.setQuantity(rs.getLong("quantity"));
        return orderItem;
    }
}
