package com.es.phoneshop.core.phone.dao;

import com.es.phoneshop.core.phone.model.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(PhoneDaoSelector selector);
    int count(PhoneDaoSelector selector);
}
