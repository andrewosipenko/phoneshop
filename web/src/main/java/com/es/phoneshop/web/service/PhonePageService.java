package com.es.phoneshop.web.service;

import com.es.phoneshop.web.page.PhonePage;

public interface PhonePageService {
    PhonePage getPhonePage(String searchQuery, String sort, String order, int pageNumber);
}
