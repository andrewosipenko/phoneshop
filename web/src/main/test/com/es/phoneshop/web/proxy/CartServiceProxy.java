package com.es.phoneshop.web.proxy;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;

import java.math.BigDecimal;
import java.util.Map;


public class CartServiceProxy implements CartService {

    private CartService cartService;

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public Cart getCart() {
        return cartService.getCart();
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException {
        cartService.addPhone(phoneId, quantity);
    }

    @Override
    public void update(Map<Long, Long> items) throws PhoneNotFoundException {
        cartService.update(items);
    }

    @Override
    public void remove(Long phoneId) throws PhoneNotFoundException {
        cartService.remove(phoneId);
    }

    @Override
    public BigDecimal getCartCost() {
        return cartService.getCartCost();
    }

    @Override
    public Long getPhonesCountInCart() {
        return cartService.getPhonesCountInCart();
    }
}
