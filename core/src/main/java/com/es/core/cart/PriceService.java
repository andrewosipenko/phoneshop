package com.es.core.cart;

import com.es.core.model.order.Order;

import java.math.BigDecimal;

public interface PriceService {

    BigDecimal obtainCartSubtotal(Cart cart);

    BigDecimal obtainCartTotal(Cart cart);

    BigDecimal getDeliveryPrice();

    void updateOrderPrice(Order order);
}
