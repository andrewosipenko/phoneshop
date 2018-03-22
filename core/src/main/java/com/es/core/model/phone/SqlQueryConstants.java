package com.es.core.model.phone;

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
    String SELECT_PHONES_OFFSET =  "SELECT * FROM phones OFFSET ";
}