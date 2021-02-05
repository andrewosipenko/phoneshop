package com.es.core.data;

import com.es.core.model.ColorModel;
import com.es.core.model.PhoneModel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class PhoneData {

    private Long id;

    private String brand;

    private String model;

    private BigDecimal price;

    private BigDecimal displaySizeInches;

    private String imageUrl;

    private Set<ColorModel> colors = new HashSet<>();

    private String description;

    private Integer stock;

    public PhoneData() {
    }

    public PhoneData(PhoneModel phone) {
        this.id = phone.getId();
        this.brand = phone.getBrand();
        this.model = phone.getModel();
        this.price = phone.getPrice();
        this.displaySizeInches = phone.getDisplaySizeInches();
        this.imageUrl = phone.getImageUrl();
        this.colors = phone.getColors();
        this.description = phone.getDescription();
        this.stock = phone.getStock();
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDisplaySizeInches() {
        return displaySizeInches;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Set<ColorModel> getColors() {
        return colors;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColors(Set<ColorModel> colors) {
        this.colors = colors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDisplaySizeInches(BigDecimal displaySizeInches) {
        this.displaySizeInches = displaySizeInches;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
