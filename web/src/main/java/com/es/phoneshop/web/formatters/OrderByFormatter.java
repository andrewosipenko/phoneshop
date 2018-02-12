package com.es.phoneshop.web.formatters;

import com.es.core.model.phone.PhoneDao;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class OrderByFormatter implements Formatter<PhoneDao.OrderBy> {

    @Override
    public PhoneDao.OrderBy parse(String text, Locale locale) throws ParseException {
        return PhoneDao.OrderBy.valueOf(text.toUpperCase().trim());
    }

    @Override
    public String print(PhoneDao.OrderBy object, Locale locale) {
        return object.name();
    }
}
