package com.es.core.form.updateForm;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateCartForm {
    private List<CartFormItem> cartFormItems;

    public List<CartFormItem> getCartFormItems() {
        return cartFormItems;
    }

    public void setCartFormItems(List<CartFormItem> cartFormItems) {
        this.cartFormItems = cartFormItems;
    }
}
