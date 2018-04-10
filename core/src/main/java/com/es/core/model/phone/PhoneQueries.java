package com.es.core.model.phone;

public interface PhoneQueries {
    String COUNT_PHONES_QUERY = "SELECT COUNT(*) FROM ( SELECT DISTINCT inStockPhones.phoneId FROM (SELECT * FROM phones LEFT JOIN stocks ON\n" +
            "id = stocks.phoneId WHERE stock > 0) AS inStockPhones LEFT JOIN phone2color on inStockPhones.id =\n" +
            "phone2color.phoneId where price > 0 GROUP BY inStockPhones.id ) AS pricedPhones ;";

    String COUNT_PHONES_BY_SEARCH_QUERY = "SELECT COUNT(*) FROM ( SELECT DISTINCT inStockPhones.phoneId FROM (SELECT * FROM phones LEFT JOIN stocks ON\n" +
            "id = stocks.phoneId WHERE stock > 0) AS inStockPhones LEFT JOIN phone2color on inStockPhones.id =\n" +
            "phone2color.phoneId where price > 0 and (lower(brand) like ? or lower(model) like ?) GROUP BY inStockPhones.id ) AS pricedPhones ;";

    String GET_BY_KEY_QUERY =  "SELECT * FROM phones " +
    "LEFT OUTER JOIN  phone2color on phones.id = phone2color.phoneId " +
    "LEFT OUTER JOIN colors on colors.id = phone2color.colorId WHERE phones.id = ?";

    String UPDATE_PHONE_QUERY = "update phones set brand = ? ,model = ? ," +
            "price = ? ,displaySizeInches = ? ,weightGr = ? ,lengthMm = ? ,widthMm = ? ," +
            "heightMm = ? ,announced = ? ,deviceType = ? ,os = ? ,displayResolution = ? ," +
            "pixelDensity = ? ,displayTechnology = ? ,backCameraMegapixels = ? ," +
            "frontCameraMegapixels = ? ,ramGb = ? ,internalStorageGb = ? ,batteryCapacityMah = ? ," +
            "talkTimeHours = ? ,standByTimeHours = ? ,bluetooth = ? ,positioning = ? ,imageUrl = ? ," +
            "description = ? where id = ? ";

    String DELETE_COLORS_QUERY = "delete from phone2color where phoneId = ?";

    String INSERT_COLORS_QUERY = "insert into phone2color (phoneId,colorId) values (?,?)";

    String GET_COLOR_IDS_FOR_PHONE = "SELECT colorId FROM phone2color WHERE phoneId = ?";

    String GET_COLORS_BY_IDS = "SELECT * FROM colors WHERE id IN (:ids)";

    String FIND_ALL_QUERY = "SELECT * FROM phones LEFT JOIN stocks ON id = phoneId WHERE stock > 0 AND price IS NOT NULL";

    String SEARCH_PART_OF_FIND_ALL_QUERY = " and (lower(brand) like ? or lower(model) like ?)";
}
