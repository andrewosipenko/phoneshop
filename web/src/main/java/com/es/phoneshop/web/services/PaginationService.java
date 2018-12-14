package com.es.phoneshop.web.services;

import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.exceptions.InvalidUrlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaginationService {

    @Autowired
    private PhoneDao phoneDao;

    public static final int PHONES_TO_DISPLAY = 10;
    private static final int PAGES_NUMBER = 10;

    public int getPageStartNumber(Integer pageNumber, String search) {
        if (pageNumber < 1) {
            throw new InvalidUrlException("Invalid page number: " + pageNumber);
        }
        pageNumber = getAvailableNewPage(pageNumber, search);
        return (pageNumber % PAGES_NUMBER != 0 || pageNumber == 0)
                ? pageNumber - (pageNumber % PAGES_NUMBER) + 1
                : pageNumber - PAGES_NUMBER;
    }

    public int getAvailableNewPage(Integer pageNumber, String search) {
        int maxPageNumber = getMaxPageNumber(search);
        return (pageNumber > maxPageNumber) ? maxPageNumber : pageNumber;
    }

    public int getMaxPageNumber(String search) {
        int maxPageNumber = (int) Math.ceil((double) (phoneDao.getNumberAvailablePhones(search) / PHONES_TO_DISPLAY));
        return maxPageNumber == 0 ? 1 : maxPageNumber;
    }

    public int getAmountPagesToDisplay(Integer startPage, String search) {
        int maxPageNumber = getMaxPageNumber(search);
        return (maxPageNumber - startPage) > PAGES_NUMBER ? PAGES_NUMBER : maxPageNumber - startPage + 1;
    }
}
