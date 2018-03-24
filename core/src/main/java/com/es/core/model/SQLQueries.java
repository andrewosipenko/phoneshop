package com.es.core.model;

public interface SQLQueries {
    String GET_PHONE_NO_COLORS = "SELECT * FROM phones WHERE id = ?";

    String GET_COLOR_IDS_FOR_PHONE = "SELECT colorId FROM phone2color WHERE phoneId = ?";

    String GET_COLORS_BY_IDS = "SELECT * FROM colors WHERE id IN (:ids) ORDER BY id";

    String SELECT_PHONES_NO_COLORS_STOCK_ONLY = "SELECT * FROM (SELECT * FROM phones LEFT JOIN stocks ON id = phoneId WHERE stock > 0 ORDER BY id) OFFSET ? LIMIT ?";

    String COUNT_PHONES_STOCK_ONLY = "SELECT COUNT(*) FROM phones LEFT JOIN stocks ON id = phoneId WHERE stock > 0";

    String INSERT_PHONE = "" +
            "INSERT INTO phones (brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, " +
                "backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
                "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) " +
            "VALUES (:brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, " +
                ":heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, " +
                ":backCameraMegapixels, :frontCameraMegapixels, :ramGb, :internalStorageGb, :batteryCapacityMah, " +
                ":talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";

    String UPDATE_PHONE = "" +
            "UPDATE phones SET brand = :brand, model = :model, price = :price, displaySizeInches = :displaySizeInches, " +
                "weightGr = :weightGr, lengthMm = :lengthMm, widthMm = :widthMm, heightMm = :heightMm, " +
                "announced = :announced, deviceType = :deviceType, os = :os, displayResolution = :displayResolution, " +
                "pixelDensity = :pixelDensity, displayTechnology = :displayTechnology, backCameraMegapixels = :backCameraMegapixels, " +
                "frontCameraMegapixels = :frontCameraMegapixels, ramGb = :ramGb, internalStorageGb = :internalStorageGb, " +
                "batteryCapacityMah = :batteryCapacityMah, talkTimeHours = :talkTimeHours, standByTimeHours = :standByTimeHours, " +
                "bluetooth = :bluetooth, positioning = :positioning, imageUrl = :imageUrl, description = :description " +
            "WHERE id = :id";

    String DELETE_COLORS_BY_PHONE_ID = "DELETE FROM phone2color WHERE phoneId = ?";

    String INSERT_COLOR_FOR_PHONE_ID = "INSERT INTO phone2color (phoneId, colorId) VALUES (?, ?)";
}
