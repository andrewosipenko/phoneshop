package com.es.core.extractor.order;

import com.es.core.model.order.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class OrderSetExtractor implements ResultSetExtractor<Order> {
    @Resource
    private OrdersSetExtractor ordersSetExtractor;

    @Override
    public Order extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Order> orders = ordersSetExtractor.extractData(resultSet);

        if (orders.size() == 0) {
            throw new IllegalArgumentException();
        }

        return orders.get(0);
    }
}
