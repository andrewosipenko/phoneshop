package com.es.core.service;

import com.es.core.exception.PaginationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationServiceImpl implements PaginationService {
    public static final int PHONES_PER_PAGE_DEFAULT_VALUE = 10;
    public static final int NUMBER_OF_PAGINATION_BUTTON_DEFAULT_VALUE = 5;

    @Override
    public List<Integer> getPaginationList(final int pageNumber) {
        if (pageNumber <= 0) {
            throw new PaginationException();
        }
        List<Integer> paginationList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PAGINATION_BUTTON_DEFAULT_VALUE; i++) {
            paginationList.add(pageNumber + i - 2);
        }
        return paginationList;
    }

    @Override
    public Integer getOffset(final int pageNumber) {
        if (pageNumber <= 0) {
            throw new PaginationException();
        }
        return (pageNumber - 1) * PHONES_PER_PAGE_DEFAULT_VALUE;
    }
}
