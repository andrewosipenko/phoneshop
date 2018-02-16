package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private HttpSession httpSession;

    @Resource
    private PhoneService phoneService;

    private static final String ATTRIBUTE_CART = "cart";
    private static final String ATTRIBUTE_COUNT_ITEMS = "countItems";
    private static final String ATTRIBUTE_PRICE = "price";

    @Override
    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute(ATTRIBUTE_CART);

        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(ATTRIBUTE_CART, cart);
            httpSession.setAttribute(ATTRIBUTE_COUNT_ITEMS, 0L);
            httpSession.setAttribute(ATTRIBUTE_PRICE, new BigDecimal(0));
        }

        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity, String color) {
        Cart cart = getCart();
        cart.addItem(phoneService.getByIdAndColor(phoneId, color).get(), quantity);
        httpSession.setAttribute(ATTRIBUTE_COUNT_ITEMS, cart.countItems());
        httpSession.setAttribute(ATTRIBUTE_PRICE, cart.getPrice());
    }

    @Override
    public void remove(Long phoneId, String color) {
        Cart cart = getCart();
        cart.removeItem(phoneId, color);
        httpSession.setAttribute(ATTRIBUTE_COUNT_ITEMS, cart.countItems());
        httpSession.setAttribute(ATTRIBUTE_PRICE, cart.getPrice());
    }

    @Override
    public long getCountItems() {
        return getCart().countItems();
    }

    @Override
    public BigDecimal getPrice() {
        return getCart().getPrice();
    }

    @Override
    public List<Phone> getAllItems() {
        Cart cart = getCart();
        Map<Phone, Long> items = cart.getItems();
        List<Phone> phones = new LinkedList<>();

        for (Phone phone : items.keySet()) {
            Long quantity = items.get(phone);
            for (int i = 0; i < quantity; i++) {
                phones.add(phone);
            }
        }

        return phones;
    }
}
