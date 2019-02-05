package com.es.phoneshop.web.services;

import com.es.core.dao.phone.PhoneDao;
import com.es.phoneshop.web.controller.exceptions.InvalidParametersInUrlException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PaginationService {
    private final String NEXT_PAGE = "next";
    private final String PREV_PAGE = "prev";

    @Resource
    private PhoneDao phoneDao;

    public static final int PHONES_TO_DISPLAY = 10;
    private static final int PAGES_NUMBER = 10;

    public int getPageStartNumber(Integer pageNumber, String search) {
        if (pageNumber < 1) {
            throw new InvalidParametersInUrlException("Invalid page number: " + pageNumber);
        }
        pageNumber = getAvailableNewPage(pageNumber, search);
        return (pageNumber % PAGES_NUMBER != 0 || pageNumber == 0)
                ? pageNumber - (pageNumber % PAGES_NUMBER) + 1
                : pageNumber - PAGES_NUMBER + 1;
    }

    public int getAvailableNewPage(Integer pageNumber, String search) {
        int maxPageNumber = getMaxPageNumber(search);
        return (pageNumber > maxPageNumber) ? maxPageNumber : pageNumber;
    }

    public int getAmountPagesToDisplay(Integer startPage, String search) {
        int maxPageNumber = getMaxPageNumber(search);
        return (maxPageNumber - startPage) > PAGES_NUMBER ? PAGES_NUMBER : maxPageNumber - startPage + 1;
    }

    public int getMaxPageNumber(String search) {
        int maxPageNumber = (int) Math.ceil((double) (phoneDao.getNumberAvailablePhones(search) / PHONES_TO_DISPLAY));
        return maxPageNumber != 0 ? maxPageNumber : 1;
    }

    public int getNewPage(Integer currentPage, String action, String search) {
        if (action.equals(NEXT_PAGE)) {
            return getPageStartNumber(currentPage + PAGES_NUMBER, search);
        } else if (action.equals(PREV_PAGE)) {
            return (currentPage - PAGES_NUMBER < 1) ? currentPage : currentPage - PAGES_NUMBER;
        } else {
            throw new InvalidParametersInUrlException("Action " + action + " is wrong!");
        }
    }
}
