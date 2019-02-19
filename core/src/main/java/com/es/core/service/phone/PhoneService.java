package com.es.core.service.phone;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface PhoneService {
    List<Phone> getPhonesByPage(int pageId, int phonesOnPage);
}
