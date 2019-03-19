package com.es.core.extractor.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.creator.OrderCreatorFromResultSet;
import com.es.core.service.creator.PhoneCreatorFromResultSet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersSetExtractor implements ResultSetExtractor<List<Order>> {
    private static final Long ZERO_VALUE = 0L;
    private static final String PHONE_ID_PARAMETER = "id";
    private static final String ORDER_ID_PARAMETER = "orderId";
    private static final String QUANTITY = "quantity";

    @Resource
    private OrderCreatorFromResultSet orderCreatorFromResultSet;

    @Resource
    private PhoneCreatorFromResultSet phoneCreatorFromResultSet;

    @Override
    public List<Order> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Order> orders = new ArrayList<>();
        Order order;

        if (resultSet.next()) {
            order = createOrder(resultSet, orders);
            addOrderItemToOrder(resultSet, order);

            while (resultSet.next()) {
                Long orderId = resultSet.getLong(ORDER_ID_PARAMETER);
                if (order.getId().equals(orderId)) {
                    addOrderItemToOrder(resultSet, order);
                } else {
                    order = createOrder(resultSet, orders);
                    addOrderItemToOrder(resultSet, order);
                }
            }
        }

        return orders;
    }

    private void addOrderItemToOrder(ResultSet resultSet, Order order) throws SQLException {
        if (!ZERO_VALUE.equals(resultSet.getLong(PHONE_ID_PARAMETER))) {
            Long quantity = resultSet.getLong(QUANTITY);
            Phone phone = phoneCreatorFromResultSet.createPhone(resultSet);
            OrderItem orderItem = new OrderItem(phone, quantity);
            order.getOrderItems().add(orderItem);
        }
    }

    private Order createOrder(ResultSet resultSet, List<Order> orders) throws SQLException {
        Order order = orderCreatorFromResultSet.createOrder(resultSet);
        order.setOrderItems(new ArrayList<>());
        orders.add(order);
        return order;
    }
}
