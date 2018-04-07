package com.es.phoneshop.core.phone.service;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.core.phone.model.Phone;

import java.util.List;

public interface PhoneService {
    int countPhones(String search);
    List<Phone> getPhones(String search, SortBy sortBy, int offset, int limit);
}
