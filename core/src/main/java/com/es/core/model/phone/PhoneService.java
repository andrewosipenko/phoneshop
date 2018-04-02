package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {
    Optional<Phone> get(Long key);
    List<Phone> findInOrder(String orderBy, int offset, int limit);
    List<Phone> findByModelInOrder(String model, String orderBy, int offset, int limit);
    long productsCount();
    long productsCountByModel(String model);
    long countProductInStock(Long id);
}
