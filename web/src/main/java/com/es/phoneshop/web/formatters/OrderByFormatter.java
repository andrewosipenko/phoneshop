package com.es.phoneshop.web.formatters;

import com.es.core.model.phone.OrderBy;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class OrderByFormatter implements Formatter<OrderBy> {

    @Override
    public OrderBy parse(String text, Locale locale) throws ParseException {
        return OrderBy.valueOf(text.toUpperCase().trim());
    }

    @Override
    public String print(OrderBy object, Locale locale) {
        return object.name();
    }
}
