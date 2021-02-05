package com.es.facade;

import com.es.core.model.PhoneModel;
import com.es.service.PhoneService;

import javax.annotation.Resource;

public class ProductDetailsPageFacade {

    @Resource
    private PhoneService phoneService;

    public PhoneModel getProductDetailPage(Long phoneId) {
        return phoneService.get(phoneId);
    }
}
