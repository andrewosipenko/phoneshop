package com.es.phoneshop.web.controller.beans;

import javax.validation.Valid;
import java.util.Map;

public class UpdateCartForm {

    @Valid
    private Map<Long, CartFormData> formData;

    public void setFormData(Map<Long, CartFormData> formData) {
        this.formData = formData;
    }

    public Map<Long, CartFormData> getFormData() {
        return formData;
    }
}
