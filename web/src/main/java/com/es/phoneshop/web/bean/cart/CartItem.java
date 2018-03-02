package com.es.phoneshop.web.bean.cart;


import com.es.core.model.phone.Phone;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CartItem {

    @NotNull(message = "{quantity.wrongFormat}")
    @Min(value = 1L, message = "{quantity.wrongFormat}")
    private Long phoneId;

    private String imageUrl;

    private String model;

    private String brand;

    @NotNull(message = "{quantity.wrongFormat}")
    @Min(value = 1L, message = "{quantity.wrongFormat}")
    private Long quantity = 0L;

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

    public CartItem() {
    }

    public CartItem(Phone phone, Long quantity) {
        this.phoneId = phone.getId();
        this.imageUrl = phone.getImageUrl();
        this.model = phone.getModel();
        this.brand = phone.getBrand();
        if(phone.getPrice() != null) {
            this.price = phone.getPrice();
        }
        this.quantity = quantity;
        this.total = phone.getPrice().multiply(new BigDecimal(quantity));
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
