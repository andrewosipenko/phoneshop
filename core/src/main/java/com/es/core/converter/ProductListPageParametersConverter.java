package com.es.core.converter;

import com.es.core.form.ProductListPageForm;
import com.es.core.model.ProductListPageParametersModel;
import com.es.core.populator.impl.ProductListPageParametersModelPopulator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class ProductListPageParametersConverter implements Converter<ProductListPageForm, ProductListPageParametersModel> {

    private List<ProductListPageParametersModelPopulator> populators;

    @Override
    public ProductListPageParametersModel convert(ProductListPageForm pageForm) {
        ProductListPageParametersModel parametersModel = new ProductListPageParametersModel();
        populators.forEach(populator -> populator.populate(pageForm, parametersModel));
        return parametersModel;
    }

    public List<ProductListPageParametersModelPopulator> getParamPopulators() {
        return populators;
    }

    public void setParamPopulators(List<ProductListPageParametersModelPopulator> paramPopulators) {
        this.populators = paramPopulators;
    }
}
