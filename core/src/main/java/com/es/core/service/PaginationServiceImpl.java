package com.es.core.service;

import com.es.core.exception.PaginationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationServiceImpl implements PaginationService {
    public static final int PHONES_PER_PAGE_DEFAULT_VALUE = 10;
    public static final int NUMBER_OF_PAGINATION_BUTTON_DEFAULT_VALUE = 5;
    public static final int RANGE_OF_PAGINATION_BUTTON_DEFAULT_VALUE = 2;

    @Override
    public List<Integer> getPaginationList(final int pageNumber) {
        if (pageNumber <= 0) {
            throw new PaginationException();
        }
        return IntStream.iterate(pageNumber - RANGE_OF_PAGINATION_BUTTON_DEFAULT_VALUE, i -> i + 1)
                .limit(NUMBER_OF_PAGINATION_BUTTON_DEFAULT_VALUE)
                .boxed()
                .toList();
    }

    @Override
    public Integer getOffset(final int pageNumber) {
        if (pageNumber <= 0) {
            throw new PaginationException();
        }
        return (pageNumber - 1) * PHONES_PER_PAGE_DEFAULT_VALUE;
    }
}
