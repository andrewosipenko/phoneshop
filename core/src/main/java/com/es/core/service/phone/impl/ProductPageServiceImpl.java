package com.es.core.service.phone.impl;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.ProductPage;
import com.es.core.model.SqlOrderByKeyword;
import com.es.core.model.phone.Phone;
import com.es.core.service.phone.ProductPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ProductPageServiceImpl implements ProductPageService {

    private static final Integer PRODUCTS_TO_DISPLAY_NUMBER = 20;

    @Resource
    private PhoneDao phoneDao;


    @Override
    public ProductPage generateProductPage(String query, String orderBy, SqlOrderByKeyword sqlOrderByKeyword, Integer page) {
        ProductPage productPage = new ProductPage(orderBy, sqlOrderByKeyword, page);
        productPage.setPhoneList(findPhonesForPage(query, orderBy, sqlOrderByKeyword, page));
        productPage.setTotalNumOFPages(getTotalNumOfPages(query));
        return productPage;
    }

    private Integer getTotalNumOfPages(String query) {
        int numOfProducts = phoneDao.getTotalNumberOfProducts(query);
        return (numOfProducts % PRODUCTS_TO_DISPLAY_NUMBER == 0 ? 0 : 1) + numOfProducts / PRODUCTS_TO_DISPLAY_NUMBER;
    }

    private List<Phone> findPhonesForPage(String query, String orderBy, SqlOrderByKeyword sqlOrderByKeyword, Integer page) {
        return phoneDao.findAll(query, orderBy, sqlOrderByKeyword, (page - 1) * PRODUCTS_TO_DISPLAY_NUMBER, PRODUCTS_TO_DISPLAY_NUMBER);
    }


    @Override
    public Optional<Phone> getPhone(Long id) {
        return phoneDao.get(id);
    }
}
