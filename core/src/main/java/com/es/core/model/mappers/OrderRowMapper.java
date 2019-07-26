package com.es.core.model.mappers;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderItemDao;
import com.es.core.model.order.OrderStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderRowMapper implements RowMapper<Order> {

    @Resource
    private OrderItemDao orderItemDao;

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
        order.setTotalPrice(new BigDecimal(resultSet.getDouble("totalPrice")));
        order.setSubtotal(new BigDecimal(resultSet.getDouble("subtotal")));
        order.setDeliveryPrice(new BigDecimal(resultSet.getDouble("deliveryPrice")));
        order.setAdditionalInformation(resultSet.getString("additionalInformation"));
        order.setContactPhoneNo(resultSet.getString("contactPhoneNo"));
        order.setDeliveryAddress(resultSet.getString("deliveryAddress"));
        order.setFirstName(resultSet.getString("firstName"));
        order.setLastName(resultSet.getString("lastName"));
        List<OrderItem> orderItemList = orderItemDao.loadOrderItemsOfOrderByOrderId(order.getId());
        order.setOrderItems(orderItemList);
        return order;
    }
}
