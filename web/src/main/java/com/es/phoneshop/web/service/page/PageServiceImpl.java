package com.es.phoneshop.web.service.page;

import com.es.phoneshop.web.bean.Pagination;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService {

    private static final int FIRST_PAGE_NUMBER = 1;

    private static final int AROUND_PAGES_COUNT = 3;

    @Override
    public Pagination getPagination(int pageNumber, int itemsCount) {
        int normalizedPageNumber = normalizePageNumber(pageNumber, itemsCount);
        int pagesCount = getPagesCount(itemsCount);

        int startPaginationNumber = Math.max(normalizedPageNumber - AROUND_PAGES_COUNT, FIRST_PAGE_NUMBER);
        int finishPaginationNumber = Math.min(normalizedPageNumber + AROUND_PAGES_COUNT, pagesCount);

        return new Pagination(normalizedPageNumber, startPaginationNumber, finishPaginationNumber);
    }

    @Override
    public int normalizePageNumber(int pageNumber, int itemsCount) {
        int normalizedPageNumber = Math.max(pageNumber, FIRST_PAGE_NUMBER);
        int lastPageNumber = getPagesCount(itemsCount);
        normalizedPageNumber = Math.min(normalizedPageNumber, lastPageNumber);
        return normalizedPageNumber;
    }

    @Override
    public int getPagesCount(int itemsCount) {
        if (itemsCount == 0) {
            return 1;
        }
        return (int) Math.ceil((double) itemsCount / AMOUNT_PHONES_ON_PAGE);
    }
}
