package com.es.core.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationServiceImpl implements PaginationService {
    public static final int PHONES_PER_PAGE = 10;
    public static final int NUMBER_OF_PAGINATION_BUTTON = 5;

    @Override
    public List<Integer> getPaginationList(final int pageNumber) {
        List<Integer> paginationList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PAGINATION_BUTTON; i++) {
            paginationList.add(pageNumber + i - 2);
        }
        return paginationList;
    }

    @Override
    public Integer getOffset(final int pageNumber) {
        return (pageNumber - 1) * PHONES_PER_PAGE;
    }
}
