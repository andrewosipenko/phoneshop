package com.es.phoneshop.web.services;

import com.es.core.model.cart.CartItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartItemValidatorTest {
    private CartItemValidator validator = new CartItemValidator();
    private CartItem cartItem;

    @Before
    public void clear() {
        cartItem  = new CartItem(1000L, 10);
    }

    @Test
    public void shouldSupportCartItemClass() {
        assertTrue(validator.supports(cartItem.getClass()));
    }

    @Test
    public void shouldNotSupportNotCartItemClass() {
        assertFalse(validator.supports(Object.class));
    }
}