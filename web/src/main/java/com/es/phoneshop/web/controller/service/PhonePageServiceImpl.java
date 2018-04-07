package com.es.phoneshop.web.controller.service;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.phone.service.PhoneService;
import com.es.phoneshop.web.controller.throwable.NoSuchPageFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhonePageServiceImpl implements PhonePageService {
    @Resource
    private PhoneService phoneService;
    private static final int PHONES_PER_PAGE = 10;

    @Override
    public int countPagesTotal(String search) {
        return phoneService.countPhones(search) / PHONES_PER_PAGE + 1;
    }

    @Override
    public List<Phone> getPhoneList(String search, SortBy sortBy, int page) throws NoSuchPageFoundException {
        int pagesTotal = countPagesTotal(search);
        if (page > pagesTotal || page <= 0)
            throw new NoSuchPageFoundException();
        int begin = (page - 1) * PHONES_PER_PAGE;
        return phoneService.getPhones(search, sortBy, begin, PHONES_PER_PAGE);
    }
}
