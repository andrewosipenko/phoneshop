package com.es.phoneshop.core.phone.service;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.core.phone.model.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {
    int countPhones(String search);

    List<Phone> getPhoneList(String search, SortBy sortBy, Integer offset, Integer limit);

    Optional<Phone> getPhone(Long id);

    void save(Phone phone);
}
