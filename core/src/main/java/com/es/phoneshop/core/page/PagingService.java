package com.es.phoneshop.core.page;

public interface PagingService {
    int getTotalPages(String search);
    String getPageURL(String search, String order);
    int[] calculatePagesNum(int page, int total);
    int getOffset(int page);
    String addSearchToOrder (String search);
}
