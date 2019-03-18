package com.es.core.extractor.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.util.OrderCreatorFromResultSet;
import com.es.core.util.PhoneCreatorFromResultSet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersSetExtractor implements ResultSetExtractor<List<Order>> {
    private final static Long ZERO_VALUE = 0L;
    private final static String PHONE_ID_PARAMETER = "id";
    private final static String ORDER_ID_PARAMETER = "orderId";

    @Override
    public List<Order> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Order> orders = new ArrayList<>();
        Order order;

        if (resultSet.next()) {
            order = createNewOrder(resultSet, orders);
            addOrderItemToOrder(resultSet, order);

            while (resultSet.next()) {
                Long orderId = resultSet.getLong(ORDER_ID_PARAMETER);
                if (order.getId().equals(orderId)) {
                    addOrderItemToOrder(resultSet, order);
                } else {
                    order = createNewOrder(resultSet, orders);
                    addOrderItemToOrder(resultSet, order);
                }
            }
        }

        return orders;
    }

    private void addOrderItemToOrder(ResultSet resultSet, Order order) throws SQLException {
        if (!ZERO_VALUE.equals(resultSet.getLong(PHONE_ID_PARAMETER))) {
            Long quantity = resultSet.getLong("quantity");
            Phone phone = PhoneCreatorFromResultSet.createPhone(resultSet);
            OrderItem orderItem = new OrderItem(phone, quantity);
            order.getOrderItems().add(orderItem);
        }
    }

    private Order createNewOrder(ResultSet resultSet, List<Order> orders) throws SQLException {
        Order order = OrderCreatorFromResultSet.createOrder(resultSet);
        order.setOrderItems(new ArrayList<>());
        orders.add(order);
        return order;
    }
}
