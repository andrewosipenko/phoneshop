package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.exceptions.phone.PhoneException;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private Cart cart;
    @Resource
    private PhoneDao phoneDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        if (!phoneDao.contains(phoneId)) {
            throw new PhoneException();
        }
        cart.addPhone(phoneId, quantity);
        refreshCart();
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            Long phoneId = item.getKey();
            Long quantity = item.getValue();
            if (!phoneDao.contains(phoneId)) {
                throw new PhoneException();
            }
            cart.getCartItems().put(phoneId, quantity);
        }
        refreshCart();
    }

    @Override
    public void remove(Long phoneId) {
        if (!cart.getCartItems().containsKey(phoneId)) {
            throw new PhoneException();
        }
        cart.getCartItems().remove(phoneId);
        refreshCart();
    }

    private void refreshCart() {
        BigDecimal newSubtotal = BigDecimal.ZERO;
        Long newAmount = 0L;
        for (Map.Entry<Long, Long> item : cart.getCartItems().entrySet()) {
            Long phoneId = item.getKey();
            Long quantity = item.getValue();
            Phone phone = phoneDao.get(phoneId).get();
            newSubtotal = newSubtotal.add(phone.getPrice().multiply(BigDecimal.valueOf(quantity)));
            newAmount += quantity;
        }
        cart.setSubtotal(newSubtotal);
        cart.setAmount(newAmount);
    }
}
