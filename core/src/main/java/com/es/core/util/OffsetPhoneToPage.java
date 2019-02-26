package com.es.core.util;

public final class OffsetPhoneToPage {
    private final static int START_SEARCH_PAGE = 1;

    private OffsetPhoneToPage() {

    }

    public static int getOffsetToPage(int page) {
        return  page == 1 ? (page - 1) : (page - 1) * 10;
    }

    public static int getPageWithSearch(int numberOfPage, int page) {
        return page <= numberOfPage ? page : START_SEARCH_PAGE;
    }
}
