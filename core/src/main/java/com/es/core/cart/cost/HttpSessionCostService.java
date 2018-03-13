package com.es.core.cart.cost;

import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class HttpSessionCostService implements CostService {
    @Resource
    private HttpSessionCartService httpSessionCartService;

    @Override
    public BigDecimal getCost() {
        Map<Phone, Long> items = httpSessionCartService.getAllItems();
        return items.keySet().stream()
                .map(phone -> phone.getPrice().multiply(BigDecimal.valueOf(items.get(phone))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
