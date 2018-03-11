package com.es.core.model;

public interface SQLQueries {
    String GET_PHONE_BY_ID = "" +
            "SELECT phones.id AS id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, " +
                "announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, " +
                "bluetooth, positioning, imageUrl, description, colors.id AS colorId, colors.code AS colorCode " +
            "FROM phones LEFT JOIN phone2color ON phones.id = phoneId LEFT JOIN colors ON colors.id = phone2color.colorId " +
            "WHERE phones.id = ?";

    String SELECT_PHONES = "" +
            "SELECT _phoneId AS id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, " +
                "announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, " +
                "bluetooth, positioning, imageUrl, description, colors.id AS colorId, colors.code AS colorCode " +
            "FROM " +
                "(SELECT id AS _phoneId, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, " +
                    "announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                    "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, " +
                    "bluetooth, positioning, imageUrl, description " +
                "FROM phones OFFSET ? LIMIT ?) " +
            "LEFT JOIN phone2color ON _phoneId = phoneId " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId";

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

    String INSERT_COLOR_FOR_PHONE_ID = "INSERT INTO phone2color (phoneId, colorId) VALUES (?,?)";
}
