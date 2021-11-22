package com.es.core.model.phone;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class Phone {

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getDisplaySizeInches() {
        return displaySizeInches;
    }

    public void setDisplaySizeInches(BigDecimal displaySizeInches) {
        this.displaySizeInches = displaySizeInches;
    }

    public Integer getWeightGr() {
        return weightGr;
    }

    public void setWeightGr(Integer weightGr) {
        this.weightGr = weightGr;
    }

    public Date getAnnounced() {
        return announced;
    }

    public void setAnnounced(Date announced) {
        this.announced = announced;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Set<Color> getColors() {
        return colors;
    }

    public void setColors(Set<Color> colors) {
        this.colors = colors;
    }

    public String getDisplayResolution() {
        return displayResolution;
    }

    public void setDisplayResolution(String displayResolution) {
        this.displayResolution = displayResolution;
    }

    public Integer getPixelDensity() {
        return pixelDensity;
    }

    public void setPixelDensity(Integer pixelDensity) {
        this.pixelDensity = pixelDensity;
    }

    public String getDisplayTechnology() {
        return displayTechnology;
    }

    public void setDisplayTechnology(String displayTechnology) {
        this.displayTechnology = displayTechnology;
    }

    public BigDecimal getBackCameraMegapixels() {
        return backCameraMegapixels;
    }

    public void setBackCameraMegapixels(BigDecimal backCameraMegapixels) {
        this.backCameraMegapixels = backCameraMegapixels;
    }

    public BigDecimal getFrontCameraMegapixels() {
        return frontCameraMegapixels;
    }

    public void setFrontCameraMegapixels(BigDecimal frontCameraMegapixels) {
        this.frontCameraMegapixels = frontCameraMegapixels;
    }

    public BigDecimal getRamGb() {
        return ramGb;
    }

    public void setRamGb(BigDecimal ramGb) {
        this.ramGb = ramGb;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getInternalStorageGb() {
        return internalStorageGb;
    }

    public void setInternalStorageGb(BigDecimal internalStorageGb) {
        this.internalStorageGb = internalStorageGb;
    }

    public Integer getBatteryCapacityMah() {
        return batteryCapacityMah;
    }

    public void setBatteryCapacityMah(Integer batteryCapacityMah) {
        this.batteryCapacityMah = batteryCapacityMah;
    }

    public BigDecimal getTalkTimeHours() {
        return talkTimeHours;
    }

    public void setTalkTimeHours(BigDecimal talkTimeHours) {
        this.talkTimeHours = talkTimeHours;
    }

    public BigDecimal getStandByTimeHours() {
        return standByTimeHours;
    }

    public void setStandByTimeHours(BigDecimal standByTimeHours) {
        this.standByTimeHours = standByTimeHours;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getPositioning() {
        return positioning;
    }

    public void setPositioning(String positioning) {
        this.positioning = positioning;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLengthMm() {
        return lengthMm;
    }

    public void setLengthMm(BigDecimal lengthMm) {
        this.lengthMm = lengthMm;
    }

    public BigDecimal getWidthMm() {
        return widthMm;
    }

    public void setWidthMm(BigDecimal widthMm) {
        this.widthMm = widthMm;
    }

    public BigDecimal getHeightMm() {
        return heightMm;
    }

    public void setHeightMm(BigDecimal heightMm) {
        this.heightMm = heightMm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", displaySizeInches=" + displaySizeInches +
                ", weightGr=" + weightGr +
                ", lengthMm=" + lengthMm +
                ", widthMm=" + widthMm +
                ", heightMm=" + heightMm +
                ", announced=" + announced +
                ", deviceType='" + deviceType + '\'' +
                ", os='" + os + '\'' +
                ", colors=" + colors +
                ", displayResolution='" + displayResolution + '\'' +
                ", pixelDensity=" + pixelDensity +
                ", displayTechnology='" + displayTechnology + '\'' +
                ", backCameraMegapixels=" + backCameraMegapixels +
                ", frontCameraMegapixels=" + frontCameraMegapixels +
                ", ramGb=" + ramGb +
                ", internalStorageGb=" + internalStorageGb +
                ", batteryCapacityMah=" + batteryCapacityMah +
                ", talkTimeHours=" + talkTimeHours +
                ", standByTimeHours=" + standByTimeHours +
                ", bluetooth='" + bluetooth + '\'' +
                ", positioning='" + positioning + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (id != null ? !id.equals(phone.id) : phone.id != null) return false;
        if (brand != null ? !brand.equals(phone.brand) : phone.brand != null) return false;
        if (model != null ? !model.equals(phone.model) : phone.model != null) return false;
        if (price != null ? !price.equals(phone.price) : phone.price != null) return false;
        if (displaySizeInches != null ? !displaySizeInches.equals(phone.displaySizeInches) : phone.displaySizeInches != null)
            return false;
        if (weightGr != null ? !weightGr.equals(phone.weightGr) : phone.weightGr != null) return false;
        if (lengthMm != null ? !lengthMm.equals(phone.lengthMm) : phone.lengthMm != null) return false;
        if (widthMm != null ? !widthMm.equals(phone.widthMm) : phone.widthMm != null) return false;
        if (heightMm != null ? !heightMm.equals(phone.heightMm) : phone.heightMm != null) return false;
        if (announced != null ? !announced.equals(phone.announced) : phone.announced != null) return false;
        if (deviceType != null ? !deviceType.equals(phone.deviceType) : phone.deviceType != null) return false;
        if (os != null ? !os.equals(phone.os) : phone.os != null) return false;
        if (colors != null ? !colors.equals(phone.colors) : phone.colors != null) return false;
        if (displayResolution != null ? !displayResolution.equals(phone.displayResolution) : phone.displayResolution != null)
            return false;
        if (pixelDensity != null ? !pixelDensity.equals(phone.pixelDensity) : phone.pixelDensity != null) return false;
        if (displayTechnology != null ? !displayTechnology.equals(phone.displayTechnology) : phone.displayTechnology != null)
            return false;
        if (backCameraMegapixels != null ? !backCameraMegapixels.equals(phone.backCameraMegapixels) : phone.backCameraMegapixels != null)
            return false;
        if (frontCameraMegapixels != null ? !frontCameraMegapixels.equals(phone.frontCameraMegapixels) : phone.frontCameraMegapixels != null)
            return false;
        if (ramGb != null ? !ramGb.equals(phone.ramGb) : phone.ramGb != null) return false;
        if (internalStorageGb != null ? !internalStorageGb.equals(phone.internalStorageGb) : phone.internalStorageGb != null)
            return false;
        if (batteryCapacityMah != null ? !batteryCapacityMah.equals(phone.batteryCapacityMah) : phone.batteryCapacityMah != null)
            return false;
        if (talkTimeHours != null ? !talkTimeHours.equals(phone.talkTimeHours) : phone.talkTimeHours != null)
            return false;
        if (standByTimeHours != null ? !standByTimeHours.equals(phone.standByTimeHours) : phone.standByTimeHours != null)
            return false;
        if (bluetooth != null ? !bluetooth.equals(phone.bluetooth) : phone.bluetooth != null) return false;
        if (positioning != null ? !positioning.equals(phone.positioning) : phone.positioning != null) return false;
        if (imageUrl != null ? !imageUrl.equals(phone.imageUrl) : phone.imageUrl != null) return false;
        return description != null ? description.equals(phone.description) : phone.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (displaySizeInches != null ? displaySizeInches.hashCode() : 0);
        result = 31 * result + (weightGr != null ? weightGr.hashCode() : 0);
        result = 31 * result + (lengthMm != null ? lengthMm.hashCode() : 0);
        result = 31 * result + (widthMm != null ? widthMm.hashCode() : 0);
        result = 31 * result + (heightMm != null ? heightMm.hashCode() : 0);
        result = 31 * result + (announced != null ? announced.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (colors != null ? colors.hashCode() : 0);
        result = 31 * result + (displayResolution != null ? displayResolution.hashCode() : 0);
        result = 31 * result + (pixelDensity != null ? pixelDensity.hashCode() : 0);
        result = 31 * result + (displayTechnology != null ? displayTechnology.hashCode() : 0);
        result = 31 * result + (backCameraMegapixels != null ? backCameraMegapixels.hashCode() : 0);
        result = 31 * result + (frontCameraMegapixels != null ? frontCameraMegapixels.hashCode() : 0);
        result = 31 * result + (ramGb != null ? ramGb.hashCode() : 0);
        result = 31 * result + (internalStorageGb != null ? internalStorageGb.hashCode() : 0);
        result = 31 * result + (batteryCapacityMah != null ? batteryCapacityMah.hashCode() : 0);
        result = 31 * result + (talkTimeHours != null ? talkTimeHours.hashCode() : 0);
        result = 31 * result + (standByTimeHours != null ? standByTimeHours.hashCode() : 0);
        result = 31 * result + (bluetooth != null ? bluetooth.hashCode() : 0);
        result = 31 * result + (positioning != null ? positioning.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
