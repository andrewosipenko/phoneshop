package com.es.core.model.cart;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class Cart {
    private Map<Long, Long> cartItems;
    private BigDecimal subtotal;
    private Long cartAmount;

    public Cart() {
        cartItems = new HashMap<>();
        subtotal = BigDecimal.ZERO;
        cartAmount = 0L;
    }

    public Map<Long, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getCartAmount() {
        return cartAmount;
    }

    public void setCartAmount(Long cartAmount) {
        this.cartAmount = cartAmount;
    }
}
