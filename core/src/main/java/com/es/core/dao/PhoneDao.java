package com.es.core.dao;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    Stock getStockFor(Long key);
    void save(Phone phone);
    List<Phone> findAllWithPositiveStock(int offset, int limit);
    //TODO: ADD TEST
    Long getTotalAmountOfPhonesWithPositiveStock();
    void makeReservationFor(Long key, Integer quantity);
    void removeReservationFor(Long key, Integer quantity);
}
