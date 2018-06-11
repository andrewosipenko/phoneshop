package com.es.core.cart;

import java.math.BigDecimal;

public interface PriceService {

    BigDecimal obtainCartSubtotal(Cart cart);

    BigDecimal obtainCartTotal(Cart cart);

    BigDecimal getDeliveryPrice();
}
