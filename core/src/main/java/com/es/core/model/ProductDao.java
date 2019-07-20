package com.es.core.model;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface ProductDao {
    List<Phone> getAllPhones();
    Phone loadPhoneById(long id);
}
