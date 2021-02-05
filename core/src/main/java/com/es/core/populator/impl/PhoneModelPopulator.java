package com.es.core.populator.impl;

import com.es.core.data.PhoneData;
import com.es.core.model.PhoneModel;
import com.es.core.populator.interfaces.Populator;

public class PhoneModelPopulator implements Populator<PhoneModel, PhoneData> {

    @Override
    public void populate(PhoneModel source, PhoneData target) {
        target.setId(source.getId());
        target.setBrand(source.getBrand());
        target.setModel(source.getModel());
        target.setImageUrl(source.getImageUrl());
        target.setDisplaySizeInches(source.getDisplaySizeInches());
        target.setDescription(source.getDescription());
        target.setColors(source.getColors());
        target.setPrice(source.getPrice());
    }
}
