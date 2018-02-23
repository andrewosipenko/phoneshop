package com.es.core.cart;

import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private HttpSession httpSession;

    @Resource
    private PhoneDao phoneDao;

    private static final String CART_ATTRIBUTE_NAME = "cart";

    @Override
    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(CART_ATTRIBUTE_NAME, cart);
        }
        return cart;
    }

    /**
     * @throws IllegalStateException - try to add a phone without price
     */
    @Override
    public void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException {
        Cart cart = getCart();
        Phone addedPhone = phoneDao.get(phoneId).orElseThrow(PhoneNotFoundException::new);
        BigDecimal phonePrice = addedPhone.getPrice();
        if (phonePrice == null) {
            throw new IllegalStateException();
        }
        cart.addPhone(addedPhone, quantity);

        recalculateCartCost(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        Cart cart = getCart();
        List<Phone> settedPhones = phoneDao.getPhonesByIdList(new ArrayList<Long>(items.keySet()));
        Map<Phone, Long> phones = settedPhones.stream()
                .collect(Collectors.toMap(phone -> phone, phone -> items.get(phone.getId())));
        cart.setItems(phones);

        recalculateCartCost(cart);
    }

    @Override
    public void remove(Long phoneId) {
        Cart cart = getCart();
        if (cart.getItems().entrySet().removeIf(e -> phoneId.equals(e.getKey().getId()))) {
            recalculateCartCost(cart);
        }
    }

    private void recalculateCartCost(Cart cart) {

        BigDecimal cartCost = cart.getItems().keySet().stream()
                .map(phone -> phone.getPrice().multiply(new BigDecimal(cart.getItems().get(phone))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setCost(cartCost);
    }
}
