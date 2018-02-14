package com.es.core.cart;

import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
            httpSession.setAttribute(ATTRIBUTE_COUNT_ITEMS, 0);
            httpSession.setAttribute(ATTRIBUTE_PRICE, new BigDecimal(0));
        }

        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Cart cart = getCart();
        cart.addItem(phoneId, quantity);

        BigDecimal price = (BigDecimal) httpSession.getAttribute(ATTRIBUTE_PRICE);
        price = price.add(phoneService.get(phoneId).get().getPrice()).multiply(BigDecimal.valueOf(quantity));

        httpSession.setAttribute(ATTRIBUTE_COUNT_ITEMS, cart.countItems());
        httpSession.setAttribute(ATTRIBUTE_PRICE, price);
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public long getCountItems() {
        Object count = httpSession.getAttribute(ATTRIBUTE_COUNT_ITEMS);
        if (count == null) {
            return 0;
        } else {
            return (long) count;
        }
    }

    @Override
    public BigDecimal getPrice() {
        Object price = httpSession.getAttribute(ATTRIBUTE_PRICE);
        if (price == null) {
            return BigDecimal.ZERO;
        } else {
            return (BigDecimal) price;
        }
    }
}
