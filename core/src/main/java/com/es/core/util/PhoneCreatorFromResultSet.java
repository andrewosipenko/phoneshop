package com.es.core.util;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PhoneCreatorFromResultSet {
    private final static String PHONE_ID_PARAMETER = "id";
    private final static String PHONE_BRAND_PARAMETER = "brand";
    private final static String PHONE_MODEL_PARAMETER = "model";
    private final static String PHONE_PRICE_PARAMETER = "price";
    private final static String PHONE_DISPLAY_SIZE_INCHES_PARAMETER = "displaySizeInches";
    private final static String PHONE_WEIGHT_GR_PARAMETER = "weightGr";
    private final static String PHONE_LENGTH_MM_PARAMETER = "lengthMm";
    private final static String PHONE_WIDTH_MM_PARAMETER = "widthMm";
    private final static String PHONE_HEIGHT_MM_PARAMETER = "heightMm";
    private final static String PHONE_ANNOUNCED_PARAMETER = "announced";
    private final static String PHONE_DEVICE_TYPE_PARAMETER = "deviceType";
    private final static String PHONE_OS_PARAMETER = "os";
    private final static String PHONE_DISPLAY_RESOLUTION_PARAMETER = "displayResolution";
    private final static String PHONE_PIXEL_DENSITY_PARAMETER = "pixelDensity";
    private final static String PHONE_DISPLAY_TECHNOLOGY_PARAMETER = "displayTechnology";
    private final static String PHONE_BACK_CAMERA_MEGAPIXELS_PARAMETER = "backCameraMegapixels";
    private final static String PHONE_FRONT_CAMERA_MEGAPIXELS_PARAMETER = "frontCameraMegapixels";
    private final static String PHONE_RAM_GB_PARAMETER = "ramGb";
    private final static String PHONE_INTERNAL_STORAGE_GB_PARAMETER = "internalStorageGb";
    private final static String PHONE_BATTERY_CAPACITY_MAH_PARAMETER = "batteryCapacityMah";
    private final static String PHONE_TALK_TIME_HOURS_PARAMETER = "talkTimeHours";
    private final static String PHONE_STAND_BY_TIME_HOURS_PARAMETER = "standByTimeHours";
    private final static String PHONE_BLUETOOTH_PARAMETER = "bluetooth";
    private final static String PHONE_POSITIONING_PARAMETER = "positioning";
    private final static String PHONE_IMAGE_URL_PARAMETER = "imageUrl";
    private final static String PHONE_DESCRIPTION_PARAMETER = "description";
    private final static String COLOR_ID_PARAMETER = "colorId";
    private final static String COLOR_CODE_PARAMETER = "code";

    private PhoneCreatorFromResultSet() {

    }

    public static Phone createPhone(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong(PHONE_ID_PARAMETER));
        phone.setBrand(resultSet.getString(PHONE_BRAND_PARAMETER));
        phone.setModel(resultSet.getString(PHONE_MODEL_PARAMETER));
        phone.setPrice(resultSet.getBigDecimal(PHONE_PRICE_PARAMETER));
        phone.setDisplaySizeInches(resultSet.getBigDecimal(PHONE_DISPLAY_SIZE_INCHES_PARAMETER));
        phone.setWeightGr(resultSet.getInt(PHONE_WEIGHT_GR_PARAMETER));
        phone.setLengthMm(resultSet.getBigDecimal(PHONE_LENGTH_MM_PARAMETER));
        phone.setWidthMm(resultSet.getBigDecimal(PHONE_WIDTH_MM_PARAMETER));
        phone.setHeightMm(resultSet.getBigDecimal(PHONE_HEIGHT_MM_PARAMETER));
        phone.setAnnounced(resultSet.getDate(PHONE_ANNOUNCED_PARAMETER));
        phone.setDeviceType(resultSet.getString(PHONE_DEVICE_TYPE_PARAMETER));
        phone.setOs(resultSet.getString(PHONE_OS_PARAMETER));
        phone.setDisplayResolution(resultSet.getString(PHONE_DISPLAY_RESOLUTION_PARAMETER));
        phone.setPixelDensity(resultSet.getInt(PHONE_PIXEL_DENSITY_PARAMETER));
        phone.setDisplayTechnology(resultSet.getString(PHONE_DISPLAY_TECHNOLOGY_PARAMETER));
        phone.setBackCameraMegapixels(resultSet.getBigDecimal(PHONE_BACK_CAMERA_MEGAPIXELS_PARAMETER));
        phone.setFrontCameraMegapixels(resultSet.getBigDecimal(PHONE_FRONT_CAMERA_MEGAPIXELS_PARAMETER));
        phone.setRamGb(resultSet.getBigDecimal(PHONE_RAM_GB_PARAMETER));
        phone.setInternalStorageGb(resultSet.getBigDecimal(PHONE_INTERNAL_STORAGE_GB_PARAMETER));
        phone.setBatteryCapacityMah(resultSet.getInt(PHONE_BATTERY_CAPACITY_MAH_PARAMETER));
        phone.setTalkTimeHours(resultSet.getBigDecimal(PHONE_TALK_TIME_HOURS_PARAMETER));
        phone.setStandByTimeHours(resultSet.getBigDecimal(PHONE_STAND_BY_TIME_HOURS_PARAMETER));
        phone.setBluetooth(resultSet.getString(PHONE_BLUETOOTH_PARAMETER));
        phone.setPositioning(resultSet.getString(PHONE_POSITIONING_PARAMETER));
        phone.setImageUrl(resultSet.getString(PHONE_IMAGE_URL_PARAMETER));
        phone.setDescription(resultSet.getString(PHONE_DESCRIPTION_PARAMETER));
        return phone;
    }

    public static Color createColor(ResultSet resultSet) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getLong(COLOR_ID_PARAMETER));
        color.setCode(resultSet.getString(COLOR_CODE_PARAMETER));
        return color;
    }
}
