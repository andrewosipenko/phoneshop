package com.es.core.dao.phoneDao;

import com.es.core.dao.CommonDao;
import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao extends CommonDao<Phone> {

    Optional<Phone> get(Long key);

    List<Phone> findAll(int offset, int limit);

}
