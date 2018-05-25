package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    List<Phone> findAllInOrder(int offset, int limit, OrderEnum order, String searchQueryString);
    Long getPhonesCount();
    Long getPhonesCountByQuery(String searchQueryString);
}
