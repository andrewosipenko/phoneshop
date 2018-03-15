package com.es.core.model.phone;

public interface PhoneQueries {
    String COUNT_PHONES_QUERY = "SELECT count(*) FROM phones";

    String GET_BY_KEY_QUERY =  "SELECT * FROM phones " +
    "JOIN  phone2color on phones.id = phone2color.phoneId " +
    "JOIN colors on colors.id = phone2color.colorId WHERE phones.id = ?";

    String UPDATE_PHONE_QUERY = "update phones set brand = ? ,model = ? ," +
            "price = ? ,displaySizeInches = ? ,weightGr = ? ,lengthMm = ? ,widthMm = ? ," +
            "heightMm = ? ,announced = ? ,deviceType = ? ,os = ? ,displayResolution = ? ," +
            "pixelDensity = ? ,displayTechnology = ? ,backCameraMegapixels = ? ," +
            "frontCameraMegapixels = ? ,ramGb = ? ,internalStorageGb = ? ,batteryCapacityMah = ? ," +
            "talkTimeHours = ? ,standByTimeHours = ? ,bluetooth = ? ,positioning = ? ,imageUrl = ? ," +
            "description = ? where id = ? ";

    String DELETE_COLORS_QUERY = "delete from phone2color where phoneId = ?";

    String INSERT_COLORS_QUERY = "insert into phone2color (phoneId,colorId) values (?,?)";

    String FIND_ALL_QUERY =  "SELECT * FROM phones %s";
}
