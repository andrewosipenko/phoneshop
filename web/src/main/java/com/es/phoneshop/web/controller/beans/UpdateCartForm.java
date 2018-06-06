package com.es.phoneshop.web.controller.beans;

import javax.validation.Valid;
import java.util.Map;

public class UpdateCartForm {

    @Valid
    private Map<Long, CartFormData> quantities;

    public void setQuantities(Map<Long, CartFormData> quantities) {
        this.quantities = quantities;
    }

    public Map<Long, CartFormData> getQuantities() {
        return quantities;
    }
}
