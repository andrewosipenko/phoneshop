package com.es.phoneshop.web.controller.service;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.core.phone.model.Phone;

import java.util.List;

public interface PhonePageService {
    int countPagesTotal(String search);
    List<Phone> getPhoneList(String search, SortBy sortBy, int pageNumber);
}
