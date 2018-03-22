package com.es.core.trigger;

import com.es.core.model.order.OrderStatus;
import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BeforeUpdateOrdersTrigger implements Trigger {

    private static final String REJECTED_STOCK_QUERY =
            "UPDATE stocks SET stocks.reserved=stocks.reserved-(SELECT orderItems.quantity from orderItems where " +
                    "orderItems.orderId = ? and orderItems.phoneId = stocks.phoneId) WHERE stocks.phoneId IN " +
                    "(SELECT orderItems.phoneId from orderItems WHERE orderItems.orderId = ?)";

    private static final String DELIVERED_STOCK_QUERY =
            "UPDATE stocks SET stocks.reserved=stocks.reserved-(SELECT orderItems.quantity from orderItems where " +
                    "orderItems.orderId = ? and orderItems.phoneId = stocks.phoneId), stocks.stock=stocks.stock-" +
                    "(SELECT orderItems.quantity from orderItems where orderItems.orderId = ? and orderItems.phoneId = " +
                    "stocks.phoneId) WHERE stocks.phoneId IN (SELECT orderItems.phoneId from orderItems " +
                    "WHERE orderItems.orderId = ?)";

    @Override
    public void init(Connection connection, String s, String s1, String s2, boolean b, int i) throws SQLException {
    }

    @Override
    public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
        OrderStatus oldStatus = OrderStatus.values()[(int) oldRow[9]];
        OrderStatus newStatus = OrderStatus.values()[(int) newRow[9]];
        ;
        Long id = (Long) oldRow[0];

        if (oldStatus == OrderStatus.NEW && oldStatus != newStatus) {
            PreparedStatement ps = null;
            switch (newStatus) {
                case DELIVERED:
                    ps = connection.prepareStatement(DELIVERED_STOCK_QUERY);
                    ps.setLong(1, id);
                    ps.setLong(2, id);
                    ps.setLong(3, id);
                    break;
                case REJECTED:
                    ps = connection.prepareStatement(REJECTED_STOCK_QUERY);
                    ps.setLong(1, id);
                    ps.setLong(2, id);
                    break;
            }

            if (ps != null) {
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public void remove() throws SQLException {
    }
}
