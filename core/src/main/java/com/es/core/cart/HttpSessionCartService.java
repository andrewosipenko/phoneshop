package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private HttpSession httpSession;

    @Resource
    private PhoneDao phoneDao;

    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static final String PHONES_COST_IN_CART_ATTRIBUTE_NAME = "cartCost";

    @Override
    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(CART_ATTRIBUTE_NAME, cart);
        }
        return cart;
    }

    @Override
    public BigDecimal getCartCost() {
        BigDecimal cartCost = (BigDecimal) httpSession.getAttribute(PHONES_COST_IN_CART_ATTRIBUTE_NAME);
        if (cartCost == null) {
            cartCost = BigDecimal.ZERO;
            httpSession.setAttribute(PHONES_COST_IN_CART_ATTRIBUTE_NAME, BigDecimal.ZERO);
        }
        return cartCost;
    }

    private void setCartCost(BigDecimal cartCost) {
        httpSession.setAttribute(PHONES_COST_IN_CART_ATTRIBUTE_NAME, cartCost);
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException {
        Cart cart = getCart();
        Phone addedPhone = phoneDao.get(phoneId).orElseThrow(PhoneNotFoundException::new);
        cart.addPhone(phoneId, quantity);
        BigDecimal cartCost = getCartCost();
        BigDecimal phonePrice = addedPhone.getPrice();
        if (phonePrice == null) {
            throw new PhoneNotFoundException();
        }
        cartCost = cartCost.add(phonePrice.multiply(new BigDecimal(quantity)));
        setCartCost(cartCost);
    }

    @Override
    public void update(Map<Long, Long> items) {
        Cart cart = getCart();

        List<Phone> settedPhones = phoneDao.getPhonesByIdList(new ArrayList<>(items.keySet()));
        cart.setItems(items);

        BigDecimal cartCost = settedPhones.stream()
                .map(phone -> phone.getPrice().multiply(new BigDecimal(items.get(phone.getId()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        setCartCost(cartCost);
    }

    @Override
    public void remove(Long phoneId) throws PhoneNotFoundException {
        Cart cart = getCart();
        Long quantity = cart.getItems().remove(phoneId);
        if (quantity != null) {
            Phone removedPhone = phoneDao.get(phoneId).orElseThrow(PhoneNotFoundException::new);
            BigDecimal cartCost = getCartCost();
            cartCost = cartCost.subtract(removedPhone.getPrice().multiply(new BigDecimal(quantity)));
            setCartCost(cartCost);
        }
    }

    @Override
    public Long getPhonesCountInCart() {
        return getCart().getCountItems();
    }
}
