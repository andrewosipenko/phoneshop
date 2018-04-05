package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.dao.PhoneDao;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

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
        cart.addPhone(phoneId, quantity);
        updateInfo(phoneId, quantity);
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private void updateInfo(Long phoneId, Long quantity) {
        updateSubtotal(phoneId, quantity);
        updateItemsAmount(quantity);
    }

    private void  updateSubtotal(Long phoneId, Long quantity){
        Phone newPhone = phoneDao.get(phoneId).orElseThrow(
                () -> new IllegalArgumentException("There is no such phone with id " + phoneId));
        BigDecimal phonePrice = newPhone.getPrice();
        if(phonePrice == null){
            throw new NullPointerException("Price of phone with id " + phoneId + " is null");
        }
        else{
            BigDecimal newSubtotal = phonePrice.multiply(new BigDecimal(quantity));
            newSubtotal = newSubtotal.add(cart.getSubtotal());
            cart.setSubtotal(newSubtotal);
        }
    }

    private void updateItemsAmount(Long quantity){
        cart.setItemsAmount(cart.getItemsAmount() + quantity);
    }
}
