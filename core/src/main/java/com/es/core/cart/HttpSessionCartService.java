package com.es.core.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private HttpSession httpSession;

    private static final String CART_ATTRIBUTE_NAME = "cart";

    @Override
    public Cart getCart() {
        Cart cart = (Cart)httpSession.getAttribute(CART_ATTRIBUTE_NAME);
        if(cart == null){
            cart = new Cart();
            httpSession.setAttribute(CART_ATTRIBUTE_NAME,cart);
        }
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Cart cart = getCart();
        cart.addPhone(phoneId,quantity);
    }

    @Override
    public void update(Map<Long, Long> items) {
        Cart cart = getCart();
        cart.setItems(items);
    }

    @Override
    public void remove(Long phoneId) {
        Cart cart = getCart();
        cart.getItems().remove(phoneId);
    }


}
