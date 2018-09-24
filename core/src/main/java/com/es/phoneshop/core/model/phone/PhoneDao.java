package com.es.phoneshop.core.model.phone;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(String order, int offset, int limit);
    List<Phone> search (String like, String order, int offset, int limit);
    List<Color> getPhoneColors(Long key);
    void savePhoneColors(Long key, Set<Color> colorSet);
    int phoneCount();
    int searchCount(String like);
    Optional<Stock> getStock(Long key);
    public void decreaseStock(final Long phoneId, final Long quantity);
}
