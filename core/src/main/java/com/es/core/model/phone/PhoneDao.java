package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit, String search);
    void delete(Phone phone);
    int getNumberAvailablePhones(String search);
    List<Phone> findAllSorted(int offset, int limit, String search, String sort, String direction);
}
