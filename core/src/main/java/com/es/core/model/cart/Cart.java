package com.es.core.model.cart;

import com.es.core.exceptions.phone.PhoneException;
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
    private Long amount;

    public Cart() {
        cartItems = new HashMap<>();
        subtotal = BigDecimal.ZERO;
        amount = 0L;
    }

    public void addPhone(Long id, Long quantity) {
        if (cartItems.containsKey(id)){
            Long oldQuantity = cartItems.get(id);
            cartItems.put(id, oldQuantity + quantity);
        } else{
            cartItems.put(id, quantity);
        }
    }

    public Long getItemQuantity(Long phoneId) {
        if (!cartItems.containsKey(phoneId)) {
            throw new PhoneException();
        }
        return cartItems.get(phoneId);
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
