package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findActivePhonesByPage(int offset, int limit);
    List<Phone> findPhonesLikeQuery(int offset, int limit, String query);
    List<Phone> sortPhones(int offset, int limit, String sort, String order);
    List<Phone> sortPhonesLikeQuery(int offset, int limit, String sort, String order, String query);
    int findPageCount(int pageSize);
    int findPageCountWithQuery(int pageSize, String query);
}
