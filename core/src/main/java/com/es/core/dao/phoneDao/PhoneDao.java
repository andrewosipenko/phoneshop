package com.es.core.dao.phoneDao;

import com.es.core.model.SqlOrderByKeyword;
import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {

    Optional<Phone> get(Long key);

    void save(Phone phone);

    List<Phone> findAll(String query, String sortBy, SqlOrderByKeyword sqlOrderByKeyword, int offset, int limit);

    int getTotalNumberOfProducts(String query);

    List<Phone> findAll(int offset, int limit);
}
