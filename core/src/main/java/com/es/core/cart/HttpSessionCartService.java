package com.es.core.cart;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.exception.NoSuchPhoneException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
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
        if(!phoneDao.contains(phoneId)){
            throw new NoSuchPhoneException();
        }
        cart.addPhone(phoneId, quantity);
        recalculateCart();
    }

    @Override
    public void update(Map<Long, Long> items) {
        for(Map.Entry<Long, Long> entry : items.entrySet()) {
            Long phoneId = entry.getKey();
            if(!phoneDao.contains(phoneId)){
                throw new NoSuchPhoneException();
            }
            Long oldQuantity = cart.getItemQuantity(phoneId);
            Long newQuantity = entry.getValue();

            cart.getItems().put(phoneId, (oldQuantity.equals(newQuantity) ? oldQuantity : newQuantity));
        }
        recalculateCart();
    }

    @Override
    public void remove(Long phoneId) {
        if(!cart.getItems().containsKey(phoneId)){
            throw new NoSuchPhoneException();
        }
        cart.getItems().remove(phoneId);
        recalculateCart();
    }

    private void recalculateCart(){
        BigDecimal newSubtotal = BigDecimal.ZERO;
        Long newQuantity = 0L;
        for(Map.Entry<Long, Long> entry : cart.getItems().entrySet()){
            Long phoneId = entry.getKey();
            Long quantity = entry.getValue();
            Phone phone = phoneDao.get(phoneId).get();

            newSubtotal = newSubtotal.add(phone.getPrice().multiply(BigDecimal.valueOf(quantity)));
            newQuantity += quantity;
        }
        cart.setSubtotal(newSubtotal);
        cart.setItemsAmount(newQuantity);
    }

    public void removePhonesOutOfTheStock(List<Phone> phones){
        List<Stock> stocks = phoneDao.getPhonesStocks(phones);
        Cart cart = getCart();
        for(Stock stock : stocks){
            Long phoneId = stock.getPhone().getId();
            Long quantity = cart.getItemQuantity(phoneId);
            if(stock.getStock().compareTo(quantity) < 0){
                remove(phoneId);
            }
        }
    }

    @Override
    public void clearCart() {
        getCart().getItems().clear();
        recalculateCart();
    }
}
