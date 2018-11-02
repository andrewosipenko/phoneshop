package com.es.phoneshop.web.controller.forms;

import javax.validation.Valid;
import java.util.List;

public class CartUpdateForm {
    @Valid
    private List<CartEntity> entities;

    public List<CartEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<CartEntity> entities) {
        this.entities = entities;
    }
}
