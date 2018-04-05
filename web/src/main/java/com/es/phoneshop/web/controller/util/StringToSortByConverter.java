package com.es.phoneshop.web.controller.util;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import org.springframework.core.convert.converter.Converter;

public class StringToSortByConverter implements Converter<String, SortBy> {
    @Override
    public SortBy convert(String s) {
        return SortBy.valueOf(s.toUpperCase());
    }
}
