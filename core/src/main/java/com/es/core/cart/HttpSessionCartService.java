package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Service
@SessionScope(proxyMode = ScopedProxyMode.INTERFACES)
public class HttpSessionCartService implements CartService {

    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @PostConstruct
    void init(){
        cart = new Cart();
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Phone phone = phoneDao.get(phoneId)
                .orElseThrow(()->new IllegalArgumentException("phoneId doesn't exist"));

        final Map<Long, CartEntry> products = cart.getProducts();
        if (products.containsKey(phoneId)){
            CartEntry cartEntry = products.get(phoneId);
            cartEntry.setQuantity(Long.sum(
                            cartEntry.getQuantity(),
                            quantity
                    ));
        } else {
            products.put(phoneId, new CartEntry(phone, quantity));
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        cart.getProducts().remove(phoneId);
    }
}
