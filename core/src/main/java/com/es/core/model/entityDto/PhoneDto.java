package com.es.core.model.entityDto;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class PhoneDto {
    private final Long id;
    private final String brand;
    private final String model;
    private final BigDecimal price;
    private final BigDecimal displaySizeInches;
    private final Integer weightGr;
    private final BigDecimal lengthMm;
    private final BigDecimal widthMm;
    private final BigDecimal heightMm;
    private final Date announced;
    private final String deviceType;
    private final String os;
    private final String displayResolution;
    private final Integer pixelDensity;
    private final String displayTechnology;
    private final BigDecimal backCameraMegapixels;
    private final BigDecimal frontCameraMegapixels;
    private final BigDecimal ramGb;
    private final BigDecimal internalStorageGb;
    private final Integer batteryCapacityMah;
    private final BigDecimal talkTimeHours;
    private final BigDecimal standByTimeHours;
    private final String bluetooth;
    private final String positioning;
    private final String imageUrl;
    private final String description;
    private Set<Color> colors = Collections.EMPTY_SET;

    public PhoneDto(Long id, String brand, String model, BigDecimal price, BigDecimal displaySizeInches,
                    Integer weightGr, BigDecimal lengthMm, BigDecimal widthMm, BigDecimal heightMm, Date announced,
                    String deviceType, String os, Set<Color> colors, String displayResolution, Integer pixelDensity,
                    String displayTechnology, BigDecimal backCameraMegapixels, BigDecimal frontCameraMegapixels,
                    BigDecimal ramGb, BigDecimal internalStorageGb, Integer batteryCapacityMah, BigDecimal talkTimeHours,
                    BigDecimal standByTimeHours, String bluetooth, String positioning, String imageUrl,
                    String description) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.displaySizeInches = displaySizeInches;
        this.weightGr = weightGr;
        this.lengthMm = lengthMm;
        this.widthMm = widthMm;
        this.heightMm = heightMm;
        this.announced = announced;
        this.deviceType = deviceType;
        this.os = os;
        this.colors = colors;
        this.displayResolution = displayResolution;
        this.pixelDensity = pixelDensity;
        this.displayTechnology = displayTechnology;
        this.backCameraMegapixels = backCameraMegapixels;
        this.frontCameraMegapixels = frontCameraMegapixels;
        this.ramGb = ramGb;
        this.internalStorageGb = internalStorageGb;
        this.batteryCapacityMah = batteryCapacityMah;
        this.talkTimeHours = talkTimeHours;
        this.standByTimeHours = standByTimeHours;
        this.bluetooth = bluetooth;
        this.positioning = positioning;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    private PhoneDto convertToDto(Phone phone) {
        return new PhoneDto(phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(),
                phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(),
                phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getColors(),
                phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(),
                phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(),
                phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(),
                phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription());
    }
}
