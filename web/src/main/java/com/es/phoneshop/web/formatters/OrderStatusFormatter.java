package com.es.phoneshop.web.formatters;

import com.es.core.model.order.OrderStatus;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import java.util.Locale;


public class OrderStatusFormatter implements Formatter<OrderStatus> {

    @Override
    public OrderStatus parse(String text, Locale locale) throws ParseException {
        return OrderStatus.valueOf(text.toUpperCase().trim());
    }

    @Override
    public String print(OrderStatus object, Locale locale) {
        return object.name();
    }
}
