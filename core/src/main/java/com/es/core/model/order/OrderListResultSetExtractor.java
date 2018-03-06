package com.es.core.model.order;


import com.es.core.model.AbstractPhoneResultSetExctractor;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderListResultSetExtractor extends AbstractPhoneResultSetExctractor implements ResultSetExtractor<List<Order>> {

    @Override
    public List<Order> extractData(ResultSet resultSet) throws SQLException {
        return null;
    }


}
