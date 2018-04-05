package com.es.core.model.phone;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Component
@EnableWebMvc
public class AddToCartForm {
    @NotNull
    private Long phoneId;

    @Min(1L)
    @NotNull
    private Long quantity;

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
