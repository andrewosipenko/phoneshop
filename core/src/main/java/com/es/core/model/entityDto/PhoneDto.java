package com.es.core.model.entityDto;

import com.es.core.model.entity.phone.Color;
import com.es.core.model.entity.phone.Phone;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class PhoneDto {
    private Long id;
    private String brand;
    private String model;
    private BigDecimal price;
    private BigDecimal displaySizeInches;
    private Integer weightGr;
    private BigDecimal lengthMm;
    private BigDecimal widthMm;
    private BigDecimal heightMm;
    private Date announced;
    private String deviceType;
    private String os;
    private Set<Color> colors = Collections.EMPTY_SET;
    private String displayResolution;
    private Integer pixelDensity;
    private String displayTechnology;
    private BigDecimal backCameraMegapixels;
    private BigDecimal frontCameraMegapixels;
    private BigDecimal ramGb;
    private BigDecimal internalStorageGb;
    private Integer batteryCapacityMah;
    private BigDecimal talkTimeHours;
    private BigDecimal standByTimeHours;
    private String bluetooth;
    private String positioning;
    private String imageUrl;
    private String description;

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
