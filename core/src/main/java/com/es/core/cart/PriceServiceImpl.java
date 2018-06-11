package com.es.core.cart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceServiceImpl implements PriceService {

    private BigDecimal deliveryPrice;

    @Value("${delivery.price}")
    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal obtainCartSubtotal(Cart cart) {
        return cart.getProducts().values().stream()
                .map(CartEntry::obtainCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal obtainCartTotal(Cart cart) {
        return obtainCartSubtotal(cart).add(deliveryPrice);
    }

    @Override
    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }
}
