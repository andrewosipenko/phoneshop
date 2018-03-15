package com.es.core.model.phone;

public interface SqlQueryConstants {
    String SQL_QUERY_PHONE_UPDATE = "update phones set" +
            " brand = ?," +
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
            " description = ?" +
            " where phones.id = ";
}