package com.es.phoneshop.web.controller.util;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class UpdateCartForm {
    @Valid
    private List<UpdateCartItem> updateCartItems = new ArrayList<>();

    public UpdateCartForm() {
    }

    public List<UpdateCartItem> getUpdateCartItems() {
        return updateCartItems;
    }

    public void setUpdateCartItems(List<UpdateCartItem> updateCartItems) {
        this.updateCartItems = updateCartItems;
    }
}
