package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private HttpSession httpSession;

    @Resource
    private PhoneService phoneService;

    private static final String ATTRIBUTE_CART = "cart";

    @Override
    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute(ATTRIBUTE_CART);

        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(ATTRIBUTE_CART, cart);
        }

        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws NoSuchElementException {
        Phone phone = getPhoneFromOptional(phoneService.get(phoneId));
        Cart cart = getCart();
        cart.getItems().merge(phone, quantity, (a, b) -> a + b);
    }

    @Override
    public void remove(Long phoneId) throws NoSuchElementException {
        Phone phone = getPhoneFromOptional(phoneService.get(phoneId));
        Cart cart = getCart();
        cart.getItems().remove(phone);
    }

    @Override
    public long getCountItems() {
        Map<Phone, Long> items = getCart().getItems();
        return items.values().stream().reduce(0L, (v1, v2) -> v1 + v2);
    }

    @Override
    public Map<Phone, Long> getAllItems() {
        return getCart().getItems();
    }

    @Override
    public void update(Map<Long, Long> phoneWithQuantity) throws NoSuchElementException {
        Map<Phone, Long> items = getCart().getItems();
        phoneWithQuantity.keySet().stream()
                .map(key -> phoneService.get(key).get())
                .forEach(phone -> items.put(phone, phoneWithQuantity.get(phone.getId())));
    }

    @Override
    public void removeProductsWhichNoInStock() {
        final Map<Phone, Long> items = getCart().getItems();
        Map<Phone, Long> newItems = items.keySet().stream()
                .filter(phone -> phoneService.countProductInStock(phone.getId()) >= items.get(phone))
                .collect(Collectors.toMap(phone -> phone, items::get));
        getCart().setItems(newItems);
    }

    private Phone getPhoneFromOptional(Optional<Phone> phone) throws NoSuchElementException {
        if (phone.isPresent()) {
            return phone.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
