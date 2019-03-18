package com.es.core.extractor.order;

import com.es.core.model.order.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderSetExtractor implements ResultSetExtractor<Order> {
    @Override
    public Order extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        OrdersSetExtractor ordersSetExtractor = new OrdersSetExtractor();
        List<Order> orders = ordersSetExtractor.extractData(resultSet);

        if (orders.size() == 0) {
            throw new IllegalArgumentException();
        }

        return orders.get(0);
    }
}
