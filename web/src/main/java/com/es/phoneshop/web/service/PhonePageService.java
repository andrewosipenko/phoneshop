package com.es.phoneshop.web.service;

import com.es.core.model.phone.OrderBy;
import com.es.phoneshop.web.bean.ProductPage;


public interface PhonePageService {
    ProductPage getPhonePage(OrderBy order, String query, int pageNumber);
}
