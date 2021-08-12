package com.es.core.dao.phoneDao;

import com.es.core.dao.CommonDao;
import com.es.core.model.SqlOrderByKeyword;
import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao extends CommonDao<Phone> {

    Optional<Phone> get(Long key);

    List<Phone> findAll(String query, String sortBy, SqlOrderByKeyword sqlOrderByKeyword, int offset, int limit);

    int getTotalNumberOfProducts(String query);
}
