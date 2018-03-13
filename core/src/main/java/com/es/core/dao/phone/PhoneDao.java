package com.es.core.dao.phone;

import com.es.core.model.phone.OrderBy;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    List<Phone> getPhonesByIdList(List<Long> idList);

    void save(Phone phone);

    List<Phone> findAll(int offset, int limit);

    List<Phone> findAllInOrder(OrderBy orderBy, int offset, int limit);

    int phoneCount();

    List<Phone> getPhonesByQuery(String query, OrderBy orderBy, int offset, int limit);

    int phoneCountByQuery(String query);

    List<Stock> getStocks(List<Long> phoneIdList);
}
