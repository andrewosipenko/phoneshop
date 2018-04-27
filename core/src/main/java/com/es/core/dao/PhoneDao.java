package com.es.core.dao;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit, String searchText);
    List<Phone> findAllSortedBy(int offset, int limit, String searchText, String sortBy, String dir);
    Integer countAvailablePhone(String search);
    boolean contains(Long key);
}
