package com.es.core.model.cart;

import com.es.core.exception.CartItemNotFindException;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private Cart cart;

    @Override
    public Cart getCart() {
        recalculateCart();
        return cart;
    }

    @Override
    public void recalculateCart() {
        cart.setTotalQuantity(cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity).sum());
        cart.setTotalCost(new BigDecimal(cart.getCartItems().stream()
                .mapToLong(value -> value.getPhone().getPrice().longValue() * value.getQuantity())
                .sum()));
    }

    @Override
    public void addPhone(Long phoneId, int quantity) throws PhoneNotFindException {
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
            recalculateCart();
        } else {
            throw new PhoneNotFindException(phoneId);
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        items.forEach((phoneId, quantity) -> {
            Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                    .filter(cartItem ->
                            cartItem.getPhone().getId().equals(phoneId))
                    .findAny();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                if (quantity == 0) {
                    remove(phoneId);
                } else {
                    cartItem.setQuantity(quantity.intValue());
                }
            } else {
                throw new CartItemNotFindException();
            }
        });
    }

    @Override
    public void remove(Long phoneId) {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findAny();
        if (optionalCartItem.isPresent()) {
            cart.getCartItems().remove(optionalCartItem.get());
            recalculateCart();
        } else {
            throw new CartItemNotFindException();
        }
    }

    @Override
    public CartItem getCartItem(Long phoneId) throws PhoneNotFindException {
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
    public void clearCart() {
        cart = new Cart();
    }
}
