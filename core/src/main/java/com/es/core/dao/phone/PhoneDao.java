package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    Phone save(Phone phone);
    List<Phone> findActivePhonesByPage(int offset, int limit);
    List<Phone> findPhonesLikeSearchText(int offset, int limit, String searchText);
    List<Phone> sortPhones(int offset, int limit, String sort, String order);
    List<Phone> sortPhonesLikeSearchText(int offset, int limit, String sort, String order, String searchText);
    int findPageCount();
    int findPageCountWithSearchText(String searchText);
}
