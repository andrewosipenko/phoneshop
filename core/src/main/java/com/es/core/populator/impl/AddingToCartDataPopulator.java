package com.es.core.populator.impl;

import com.es.core.form.AddingToCartForm;
import com.es.core.model.AddingToCartModel;
import com.es.core.populator.interfaces.Populator;

public class AddingToCartDataPopulator implements Populator<AddingToCartForm, AddingToCartModel> {

    @Override
    public void populate(AddingToCartForm data, AddingToCartModel model) {
        model.setQuantityToAdd(data.getQuantityToAdd());
        model.setIdOfAddingPhone(data.getProductId());
    }
}
