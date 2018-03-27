package com.es.phoneshop.web.service;

import com.es.core.model.phone.OrderEnum;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.bean.pagination.CurrentPhonePage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductListPageService {
    @Resource
    private PhoneDao phoneDao;

    public CurrentPhonePage getCurrentPage(int offset, int limit, OrderEnum order, String searchQueryString, int currentPageNumber) {
        List<Phone> phoneList =  phoneDao.findAllInOrder(offset, limit, order, searchQueryString);
        return new CurrentPhonePage(currentPageNumber, phoneList);
    }
}
