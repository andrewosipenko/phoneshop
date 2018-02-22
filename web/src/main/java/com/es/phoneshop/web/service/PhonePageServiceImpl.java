package com.es.phoneshop.web.service;

import com.es.core.model.phone.OrderBy;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.bean.Pagination;
import com.es.phoneshop.web.bean.ProductPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhonePageServiceImpl implements PhonePageService {

    private static int AMOUNT_PHONES_ON_PAGE = 10;

    private static final int FIRST_PAGE_NUMBER = 1;

    private static final int AROUND_PAGES_COUNT = 3;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public ProductPage getPhonePage(OrderBy order, String query, int pageNumber) {
        int phoneCount = getPhoneCount(query);
        int pagesCount = getPagesCount(phoneCount);
        pageNumber = normalizePageNumber(pageNumber, phoneCount);

        int offset = ((pageNumber - 1) * AMOUNT_PHONES_ON_PAGE);
        int limit = AMOUNT_PHONES_ON_PAGE;

        List<Phone> phones = getPhoneList(query, order, offset, limit);

        int startPaginationNumber = Math.max(pageNumber - AROUND_PAGES_COUNT, FIRST_PAGE_NUMBER);
        int finishPaginationNumber = Math.min(pageNumber + AROUND_PAGES_COUNT, pagesCount);

        Pagination pagination = new Pagination(pageNumber, startPaginationNumber, finishPaginationNumber);

        ProductPage productPage = new ProductPage(phoneCount,phones,pagination);

        return productPage;
    }

    private int getPhoneCount(String query) {
        if (query == null) {
            return phoneDao.phoneCount();
        }
        return phoneDao.phoneCountByQuery(query);
    }

    private List<Phone> getPhoneList(String query, OrderBy order, int offset, int limit) {
        if (query == null) {
            return phoneDao.findAllInOrder(order, offset, limit);
        }
        return phoneDao.getPhonesByQuery(query, order, offset, limit);
    }

    private int normalizePageNumber(int pageNumber, int phonesCount) {
        pageNumber = Math.max(pageNumber, FIRST_PAGE_NUMBER);
        int lastPageNumber = getPagesCount(phonesCount);
        pageNumber = Math.min(pageNumber, lastPageNumber);
        return pageNumber;
    }

    private int getPagesCount(int phonesCount) {
        if (phonesCount == 0) {
            return 1;
        }
        return (int) Math.ceil((double) phonesCount / AMOUNT_PHONES_ON_PAGE);
    }

}
