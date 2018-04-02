package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findByModelInOrder(String model, String orderBy, int offset, int limit);
    long productsCountWithModel(String model);
    long countProductInStock(Long id);
}
