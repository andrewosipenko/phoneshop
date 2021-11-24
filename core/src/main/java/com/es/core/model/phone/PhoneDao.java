package com.es.core.model.phone;

import com.es.core.model.color.Color;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    Optional<Color> getColor(Long key);
    Set<Color> getColorsByPhoneId(Long key);
    Optional<Stock> getStock(Long key);
}
