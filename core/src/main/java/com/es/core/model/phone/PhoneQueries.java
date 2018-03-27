package com.es.core.model.phone;

public interface PhoneQueries {
    String COUNT_PHONES_QUERY = "SELECT count(*) FROM phones";

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

    String FIND_ALL_QUERY =  "SELECT * FROM" +
            " (SELECT * FROM phones LEFT JOIN stocks ON id = phoneId WHERE stock > 0) AS phones" +
            " LEFT OUTER JOIN  phone2color on phones.id = phone2color.phoneId" +
            " LEFT OUTER JOIN colors on colors.id = phone2color.colorId WHERE price > 0" ;

    String SEARCH_PART_OF_FIND_ALL_QUERY = " and (lower(brand) like ? or lower(model) like ?)";
}
