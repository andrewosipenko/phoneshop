package com.es.phoneshop.web.controller.pages.dto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class UpdateCartRequest {
    @Valid
    Map<Long, QuantityForm> quantityFormMap;

    public UpdateCartRequest() {
        quantityFormMap = new HashMap<>();
    }

    public Map<Long, QuantityForm> getQuantityFormMap() {
        return quantityFormMap;
    }

    public void setQuantityFormMap(Map<Long, QuantityForm> quantityFormMap) {
        this.quantityFormMap = quantityFormMap;
    }
}
