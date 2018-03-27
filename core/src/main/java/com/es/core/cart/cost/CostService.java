package com.es.core.cart.cost;

import com.es.core.cart.Cart;

import java.math.BigDecimal;

public interface CostService {
    BigDecimal getCost(Cart cart);
}
