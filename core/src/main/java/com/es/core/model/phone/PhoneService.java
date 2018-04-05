package com.es.core.model.phone;

import java.util.List;

public interface PhoneService {
    List<Phone> findAll(int offset, int limit, String sortBy);

    int countAll();

    List<Phone> searchByModel(String keyString, int limit, int offset, String sortBy);

    int countSearchResult(String keyString);
}
