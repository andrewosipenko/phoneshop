package com.es.core.dao;

public interface SqlQueryConstants {
    interface PhoneDao{
        String PHONE_VALUES = " brand = ?," +
                " model = ?," +
                " price = ?," +
                " displaySizeInches = ?," +
                " weightGr = ?," +
                " lengthMm = ?," +
                " widthMm = ?," +
                " heightMm = ?," +
                " announced = ?," +
                " deviceType = ?," +
                " os = ?," +
                " displayResolution = ?," +
                " pixelDensity = ?," +
                " displayTechnology = ?," +
                " backCameraMegapixels = ?," +
                " frontCameraMegapixels = ?," +
                " ramGb = ?," +
                " internalStorageGb = ?," +
                " batteryCapacityMah = ?," +
                " talkTimeHours = ?," +
                " standByTimeHours = ?," +
                " bluetooth = ?," +
                " positioning = ?," +
                " imageUrl = ?," +
                " description = ?";
        String PHONE_UPDATE = "UPDATE phones SET" + PHONE_VALUES + "WHERE phones.id = ";
        String COUNT_PHONES = "SELECT COUNT(*) FROM phones ";
        String SELECT_PHONES_BY_ID =  "SELECT * FROM phones WHERE phones.id = ";
        String COUNT_PHONES_WITH_ID = COUNT_PHONES + "WHERE phones.id = ";
        String INSERT_INTO_PHONE2COLOR = "INSERT INTO phone2color (phoneId, colorId) VALUES (?, ?) ";
        String DELETE_FROM_PHONE2COLOR_BY_PHONE_ID = "DELETE FROM phone2color WHERE phone2color.phoneId = ";
        String SELECT_COLORS_BELONGS_TO_PHONE_ID = "SELECT * FROM colors INNER JOIN phone2color " +
                "ON colors.id = phone2color.colorId WHERE phone2color.phoneId = ";
        String AVAILABLE_PHONES = "INNER JOIN stocks ON stocks.phoneId = phones.id WHERE stocks.stock > 0" +
                " AND phones.price IS NOT NULL ";
        String SELECT_AVAILABLE_PHONES_WITH_OFFSET =  "SELECT * FROM phones " + AVAILABLE_PHONES + " AND lower(brand) like ? OFFSET ";
        String SELECT_AVAILABLE_PHONES_SORTED = "SELECT * FROM phones " + AVAILABLE_PHONES + " AND lower(brand) like ? ORDER BY phones.";
    }
    interface OrderDao{
        String SET_PHONE_ID_IN_ORDER_ITEM = "UPDATE orderItems SET phoneId = ? WHERE id = ?";
        String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ";
        String SET_ORDER_ID_IN_ORDER_ITEM = "UPDATE orderItems SET orderId = ? WHERE id = ?";
        String INSERT_ORDER_ITEM_BELONG_TO_ORDER = "INSERT INTO order2orderItem (orderId, orderItemId) VALUES (?, ?) ";
        String SELECT_ORDER_ITEMS_BELONG_TO_ORDER = "SELECT * FROM orderItems INNER JOIN order2orderItem ON orderItems.id = order2orderItem.orderItemId WHERE order2orderItem.orderId = ";
        String SELECT_PHONE_ID_BELONG_TO_ORDER_ITEM = "SELECT phoneId FROM orderItems WHERE orderItems.id = ";
        String DELETE_ORDER_ITEMS_BELONGS_TO_ORDER ="DELETE FROM orderItems WHERE orderId = ";
        String INSERT_ORDER_KEY = "INSERT INTO orderId2orderKey (orderId, orderKey) VALUES (?, ?)";
        String SELECT_ORDER_ID_BY_ORDER_KEY = "SELECT orderId FROM orderId2orderKey WHERE orderKey = ";
        String SELECT_ORDER_KEY_BY_ORDER_ID = "SELECT orderKey FROM orderId2OrderKey WHERE orderId = ";
        String COUNT_ORDER_KEY = "SELECT COUNT(*) FROM orderId2orderKey WHERE orderKey = ";
        String SELECT_ALL = "SELECT * FROM orders";
    }
    interface StockDao{
        String SELECT_STOCK_BY_PHONE_ID = "SELECT * from stocks WHERE phoneId = ";
        String UPDATE_STOCK_BY_PHONE_ID = "UPDATE stocks SET stock = :stock, reserved = :reserved WHERE phoneId = :phone.id";
    }
}