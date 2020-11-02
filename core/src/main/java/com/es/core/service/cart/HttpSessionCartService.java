package com.es.core.service.cart;

import com.es.core.model.DAO.phone.PhoneDao;
import com.es.core.model.entity.cart.Cart;
import com.es.core.model.entity.cart.CartItem;
import com.es.core.model.entity.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService<HttpSession> {

    private static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName() + ".cart";

    private final PhoneDao phoneDao;

    @Autowired
    public HttpSessionCartService(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public Cart getCart(HttpSession httpSession) {
        synchronized (httpSession) {
            Cart cart = (Cart) httpSession.getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                httpSession.setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        }
    }

    @Override
    public void addPhone(Cart cart, Long phoneId, Long quantity) {
        try {
            Phone phone = phoneDao.get(phoneId).get();
            Optional<CartItem> optionalCartItem = findItemInCart(cart, phoneId);
            if (optionalCartItem.isPresent()) {
                var cartItem = optionalCartItem.get();
                increaseQuantity(cartItem, quantity);
            } else {
                cart.getItems().add(new CartItem(phone, quantity));
            }
            recalculateCart(cart);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(String.valueOf(phoneId));
        }
    }

    @Override
    public void update(Cart cart, Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Cart cart, Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private void recalculateCart(Cart cart) {
        recalculateTotalCost(cart);
        recalculateTotalQuantity(cart);
    }

    private void recalculateTotalCost(Cart cart) {
        cart.setTotalCost(cart.getItems()
                .stream()
                .map(this::getCartItemTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO));
    }

    private void recalculateTotalQuantity(Cart cart) {
        cart.setTotalQuantity(cart.getItems()
                .stream()
                .map(CartItem::getQuantity)
                .reduce(Long::sum)
                .orElse(0L));
    }

    private Optional<CartItem> findItemInCart(Cart cart, Long productId) {
        return cart.getItems()
                .stream()
                .filter(existingCartItem -> existingCartItem.getProduct().getId().equals(productId))
                .findAny();
    }

    private BigDecimal getCartItemTotalPrice(CartItem cartItem) {
        var phonePrice = cartItem.getProduct().getPrice();
        if(phonePrice != null) {
            return phonePrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        } else return BigDecimal.ZERO;
    }

    private void increaseQuantity(CartItem cartItem, Long quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }
}
