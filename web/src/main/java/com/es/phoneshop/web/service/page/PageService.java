package com.es.phoneshop.web.service.page;

import com.es.phoneshop.web.bean.Pagination;

public interface PageService {

    int AMOUNT_PHONES_ON_PAGE = 10;

    int getPagesCount(int itemsCount);

    int normalizePageNumber(int pageNumber, int itemsCount);

    Pagination getPagination(int pageNumber, int itemsCount);
}
