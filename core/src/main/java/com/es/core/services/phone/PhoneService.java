package com.es.core.services.phone;

import com.es.core.model.phone.Phone;

import java.util.List;
//TODO: add tests
public interface PhoneService {
    List<Phone> getPhonesWithPositiveStock(int offset, int limit);

    List<Phone> getPhonesByKeyword(String keyword);

    Long getTotalAmountOfPhonesWithPositiveStock();
}
