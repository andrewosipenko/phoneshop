package com.es.core.model.cart;

import com.es.core.exception.CartItemNotFindException;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    public static final String SESSION_CART = "sessionCart";
    @Resource
    private PhoneDao phoneDao;

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(SESSION_CART);
        if (cart == null){
            cart = new Cart();
            session.setAttribute(SESSION_CART, cart);
        } else {
            recalculateCart(cart);
        }
        return cart;
    }

    @Override
    public void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity).sum());
        cart.setTotalCost(new BigDecimal(cart.getCartItems().stream()
                .mapToLong(value -> value.getPhone().getPrice().longValue() * value.getQuantity())
                .sum()));
    }

    @Override
    public void addPhone(Long phoneId, int quantity, Cart cart) throws PhoneNotFindException {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findAny();
        Optional<Phone> optionalPhone = phoneDao.get(phoneId);
        if (optionalPhone.isPresent()) {
            if (optionalCartItem.isPresent()) {
                optionalCartItem.get().addQuantity(quantity);
            } else {
                cart.getCartItems().add(new CartItem(optionalPhone.get(), quantity));
            }
            recalculateCart(cart);
        } else {
            throw new PhoneNotFindException(phoneId);
        }
    }

    @Override
    public void update(Map<Long, Long> items, Cart cart) {
        items.forEach((phoneId, quantity) -> {
            Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                    .filter(cartItem ->
                            cartItem.getPhone().getId().equals(phoneId))
                    .findAny();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                if (quantity == 0) {
                    remove(phoneId, cart);
                } else {
                    cartItem.setQuantity(quantity.intValue());
                }
            } else {
                throw new CartItemNotFindException();
            }
        });
    }

    @Override
    public void remove(Long phoneId, Cart cart) {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findAny();
        if (optionalCartItem.isPresent()) {
            cart.getCartItems().remove(optionalCartItem.get());
            recalculateCart(cart);
        } else {
            throw new CartItemNotFindException();
        }
    }

    @Override
    public CartItem getCartItem(Long phoneId, Cart cart) throws PhoneNotFindException {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findAny();
        if (optionalCartItem.isPresent()) {
            return optionalCartItem.get();
        } else {
            throw new PhoneNotFindException(phoneId);
        }
    }

    @Override
    public void clearCart(HttpSession session) {
        session.removeAttribute(SESSION_CART);
    }
}
