package com.es.core.service.price;

import com.es.core.model.cart.Cart;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceServiceImpl implements PriceService{
    @Override
    public void recalculatePrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getPhone().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }
}
