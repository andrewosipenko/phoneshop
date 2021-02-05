package com.es.core.populator.impl;

import com.es.core.form.ProductListPageForm;
import com.es.core.model.ProductListPageParametersModel;
import com.es.core.populator.interfaces.Populator;

public class ProductListPageParametersModelPopulator implements Populator<ProductListPageForm, ProductListPageParametersModel> {

    @Override
    public void populate(ProductListPageForm data, ProductListPageParametersModel model) {
        model.setFieldToSort(data.getFieldToSort());
        model.setOrderToSort(data.getOrderToSort());
        model.setPhoneNameQuery(data.getPhoneNameQuery());
        model.setPage(data.getPage());
        model.setLimit(0);
        model.setOffset(0);
    }
}
