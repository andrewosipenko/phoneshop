package com.es.core.cart;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.exception.NoSuchPhoneException;
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
    public void addPhone(Long phoneId, Long quantity){
        updateInfo(phoneId, quantity);
        cart.addPhone(phoneId, quantity);
    }

    @Override
    public void update(Map<Long, Long> items) {
        for(Map.Entry<Long, Long> entry : items.entrySet()) {
            Long phoneId = entry.getKey();
            Long oldQuantity = cart.getItemQuantity(phoneId);
            Long newQuantity = entry.getValue();

            cart.getItems().put(phoneId, (oldQuantity.equals(newQuantity) ? oldQuantity : newQuantity));
            updateInfo(phoneId, newQuantity - oldQuantity);
        }
    }

    @Override
    public void remove(Long phoneId) {
        updateInfo(phoneId, -cart.getItemQuantity(phoneId));
        cart.getItems().remove(phoneId);
    }

    private void updateInfo(Long phoneId, Long deltaQuantity){
        updateSubtotal(phoneId, deltaQuantity);
        updateItemsAmount(deltaQuantity);
    }

    private void  updateSubtotal(Long phoneId, Long deltaQuantity){
        Phone newPhone = phoneDao.get(phoneId).orElseThrow(NoSuchPhoneException::new);
        BigDecimal phonePrice = newPhone.getPrice();
        BigDecimal newSubtotal = phonePrice.multiply(new BigDecimal(deltaQuantity));
        newSubtotal = newSubtotal.add(cart.getSubtotal());
        cart.setSubtotal(newSubtotal);
    }

    private void updateItemsAmount(Long deltaQuantity){
        cart.setItemsAmount(cart.getItemsAmount() + deltaQuantity);
    }
}
