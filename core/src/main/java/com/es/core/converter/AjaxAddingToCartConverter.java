package com.es.core.converter;

import com.es.core.form.AddingToCartForm;
import com.es.core.model.AddingToCartModel;
import com.es.core.populator.impl.AddingToCartDataPopulator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class AjaxAddingToCartConverter implements Converter<AddingToCartForm, AddingToCartModel> {

    private List<AddingToCartDataPopulator> populators;

    @Override
    public AddingToCartModel convert(AddingToCartForm ajaxAddingToCartForm) {
        AddingToCartModel model = new AddingToCartModel();
        populators.forEach(populator -> populator.populate(ajaxAddingToCartForm, model));
        return model;
    }

    public void setAjaxAddingToCartDataPopulators(List<AddingToCartDataPopulator> ajaxAddingToCartDataPopulators) {
        this.populators = ajaxAddingToCartDataPopulators;
    }
}
