package com.es.phoneshop.web.controller.beans;

import javax.validation.Valid;
import java.util.Map;

public class UpdateCartForm {

    @Valid
    private Map<Long, QuantityForm> formData;

    public void setFormData(Map<Long, QuantityForm> formData) {
        this.formData = formData;
    }

    public Map<Long, QuantityForm> getFormData() {
        return formData;
    }
}
