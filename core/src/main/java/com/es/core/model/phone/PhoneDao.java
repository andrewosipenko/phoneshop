package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit, String sortBy);
    List<Phone> searchByModel(String keyWord, int limit, int offset, String sortBy);
    int countSearchResult(String keyWord);
    int countAll();
}
