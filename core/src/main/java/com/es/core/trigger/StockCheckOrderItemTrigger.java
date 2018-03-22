package com.es.core.trigger;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockCheckOrderItemTrigger implements Trigger {

    private static final String UPDATE_STOCK_QUERY =
            "UPDATE stocks SET reserved=reserved+? WHERE phoneId = ? " +
                    "AND stock >= reserved + ?";

    @Override
    public void init(Connection connection, String s, String s1, String s2, boolean b, int i) throws SQLException {
    }

    @Override
    public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
        Long phoneId = getPhoneId(oldRow, newRow);
        Long quantity = getQuantity(oldRow, newRow);
        if (phoneId == null) {
            throw new SQLException();
        }
        PreparedStatement prep = connection.prepareStatement(UPDATE_STOCK_QUERY);
        prep.setLong(1, quantity);
        prep.setLong(2, phoneId);
        prep.setLong(3, quantity);
        if (prep.executeUpdate() == 0) {
            throw new SQLException();
        }
    }

    private Long getQuantity(Object[] oldRow, Object[] newRow) {
        Long newQuantity = 0L;
        Long oldQuantity = 0L;
        if (oldRow != null) {
            oldQuantity = (Long) oldRow[3];
        }

        if (newRow != null) {
            newQuantity = (Long) newRow[3];
        }

        return newQuantity - oldQuantity;
    }

    private Long getPhoneId(Object[] oldRow, Object[] newRow) {
        if (oldRow != null) {
            return (Long) oldRow[2];
        } else if (newRow != null) {
            return (Long) newRow[2];
        }
        return null;
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public void remove() throws SQLException {
    }
}
