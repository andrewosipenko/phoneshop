package com.es.core.model;

import com.es.core.model.phone.Phone;

import java.util.Optional;

public interface ProductDao {
    Optional<Phone> load(Long key);
}
