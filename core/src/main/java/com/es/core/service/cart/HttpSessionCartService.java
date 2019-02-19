package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
    private final static ApplicationContext context = new ClassPathXmlApplicationContext("context/applicationContext-core.xml");

    @Resource
    private PhoneDao phoneDao;

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

        recalculatePrice(cart);
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
        return context.getBean("cart", Cart.class);
    }

    private void recalculatePrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getPhone().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }
}
