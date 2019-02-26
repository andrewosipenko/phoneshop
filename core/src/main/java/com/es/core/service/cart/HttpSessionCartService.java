package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.price.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    private final static String CART_ATTRIBUTE = "cart";

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private PriceService priceService;

    @Autowired( required = false)
    private Cart cart;

    @Override
    public void addCartItem(Cart cart, Long phoneId, Long quantity) {
        List<CartItem> cartItems = cart.getCartItems();
        Phone phone = phoneDao.get(phoneId).orElseThrow(IllegalArgumentException::new);

        Optional<CartItem> optionalCartItem = cartItems.stream()
                .findAny()
                .filter(item -> phoneId.equals(item.getPhone().getId()));

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(quantity, phone);
            cartItems.add(cartItem);
        }

        priceService.recalculatePrice(cart);
    }

    @Override
    public void update(Cart cart, Map<Long, Long> items) {

    }

    @Override
    public void update(Cart cart, Long phoneId, Long quantity) {
        List<CartItem> cartItems = cart.getCartItems();
    }

    @Override
    public void remove(Cart cart, Long phoneId) {
    }

    @Override
    public Cart getCart() {
        return cart;
    }
}
