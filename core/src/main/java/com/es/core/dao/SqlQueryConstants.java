package com.es.core.dao;

public interface SqlQueryConstants {
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