package com.es.core.service.phone;

import com.es.core.model.ProductPage;
import com.es.core.model.SqlOrderByKeyword;
import com.es.core.model.phone.Phone;

import java.util.Optional;

public interface ProductPageService {

    ProductPage generateProductPage(String query, String orderBy, SqlOrderByKeyword sqlOrderByKeyword, Integer page);

    Optional<Phone> getPhone(Long id);
}
