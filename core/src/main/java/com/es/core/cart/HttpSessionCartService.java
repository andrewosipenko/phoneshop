package com.es.core.cart;

import com.es.core.exceptions.PhoneNotFoundException;
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
    public static final String CART_SESSION_ATTRIBUTE = "cart";
    @Resource
    private PhoneDao phoneDao;

    @Override
    public Cart getCart(HttpSession session) {
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                session.setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        }
    }

    @Override
    public void addPhone(Cart cart, Long phoneId, Long quantity) {
        synchronized (cart) {
            Optional<Phone> optionalPhone = phoneDao.get(phoneId);
            if (!optionalPhone.isPresent()) {
                throw new PhoneNotFoundException();
            }
            Phone phone = optionalPhone.get();
            Optional<CartItem> optionalCartItem = cart.getItems().stream()
                    .filter(item -> item.getPhone().getId().equals(phoneId))
                    .findAny();
            long updatedQuantity = quantity + optionalCartItem.map(CartItem::getQuantity).orElse(0L);
            if (optionalCartItem.isPresent()) {
                optionalCartItem.get().setQuantity(updatedQuantity);
            } else {
                cart.getItems().add(new CartItem(phone, quantity));
            }
            recalculateCart(cart);
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .reduce(0L, Long::sum));
        cart.setTotalCost(cart.getItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice()
                        .multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(new BigDecimal(0), BigDecimal::add));
    }
}
