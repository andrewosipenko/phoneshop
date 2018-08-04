package com.es.phoneshop.core.cart.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "${cart.scope}", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal subtotal = BigDecimal.ZERO;
    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public long getPhonesTotal() {
        return cartItems.stream()
                .reduce(0L, (num, item) -> num + item.getQuantity(), Long::sum);
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
