package com.es.core.service.phone;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {
    Optional<Phone> get(Long key);
    List<Phone> getPhonesByPage(int pageId, int phonesOnPage);
}
