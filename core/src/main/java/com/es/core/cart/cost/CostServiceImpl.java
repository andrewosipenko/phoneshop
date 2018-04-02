package com.es.core.cart.cost;

import com.es.core.cart.Cart;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CostServiceImpl implements CostService {
    @Override
    public BigDecimal getCost(Cart cart) {
        Map<Phone, Long> items = cart.getItems();
        return items.keySet().stream()
                .map(phone -> phone.getPrice().multiply(BigDecimal.valueOf(items.get(phone))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
