package com.es.core.model.phone;

import com.es.core.model.color.Color;
import com.es.core.model.enums.SortField;
import com.es.core.model.enums.SortOrder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    List<Phone> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder, String searchText);
    Optional<Color> getColor(Long key);
    Set<Color> getColorsByPhoneId(Long key);
    Optional<Stock> getStock(Long key);
}
