package com.es.core.cart;

import com.es.core.exception.CartItemNotFindException;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
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
                .mapToLong(value -> value.getPhone().getPrice().longValue())
                .sum()));
    }

    @Override
    public void addPhone(Long phoneId, Integer quantity) throws PhoneNotFindException {
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
            throw new PhoneNotFindException("Phone(" + phoneId + ") is not found");
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findAny();
        if (optionalCartItem.isPresent()) {
            cart.getCartItems().remove(optionalCartItem.get());
        } else {
            throw new CartItemNotFindException("CartItem is not found");
        }
    }
}
