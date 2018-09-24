package com.es.phoneshop.core.cart;

import com.es.phoneshop.core.model.phone.Phone;
import com.es.phoneshop.core.model.phone.PhoneDao;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneDao phoneDao;

    private Cart cart;

    public HttpSessionCartService() {
        cart = new Cart();
    }

    @Override
    public Cart getCart() {
        if(cart == null) {
            cart = new Cart();
            return cart;
        } else {
            return cart;
        }
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Map<Long, Long> cartItems = cart.getCartItems();
        Long previousQuantity = cartItems.get(phoneId);

        if(previousQuantity != null) {
            cartItems.put(phoneId, previousQuantity + quantity);
        } else {
            cartItems.put(phoneId, quantity);
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        Map<Long, Long> initCartItems = cart.getCartItems();

      for(Map.Entry<Long, Long> entry : items.entrySet()) {
          initCartItems.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().remove(phoneId);
    }

    @Override
    public long getItemsNum() {
        Long quantities = 0L;
        Set<Map.Entry<Long, Long>> cartSet = cart.getCartItems().entrySet();
        Iterator<Map.Entry<Long, Long>> iterator = cartSet.iterator();

        while(iterator.hasNext()) {
            quantities += iterator.next().getValue();
        }

        return quantities;
    }

    @Override
    public long getItemNumById(Long phoneId) {
        Long quantity = cart.getCartItems().get(phoneId);

        if(quantity != null) {
            return quantity;
        } else {
            return 0L;
        }
    }

    @Override
    public int getOverallPrice() {
        BigDecimal price = new BigDecimal(0);
        Set<Map.Entry<Long, Long>> cartSet = cart.getCartItems().entrySet();

        for(Map.Entry<Long,Long> entry : cartSet) {
            price = price.add(phoneDao.get(entry.getKey()).get().getPrice().multiply(new BigDecimal(entry.getValue())));
        }

        return price.intValue();
     }

     @Override
     public boolean isNotEnoughStock(Long phoneId, Long quantity) {
        return quantity > phoneDao.getStock(phoneId).get().getStock() - getItemNumById(phoneId);
     }

     @Override
     public void setColors(Phone phone) {
        phone.setColors(new HashSet<>(phoneDao.getPhoneColors(phone.getId())));
    }

     @Override
     public Map<Phone,Long> getPhoneMap() {
        Set<Map.Entry<Long, Long>> cartEntrySet = cart.getCartItems().entrySet();
        Map<Phone, Long> phoneMap = new HashMap<>();

        for(Map.Entry<Long, Long> entry: cartEntrySet) {
            Optional<Phone> phone = phoneDao.get(entry.getKey());

            if(phone.isPresent()) {
                Phone gotPhone = phone.get();
                setColors(gotPhone);
                phoneMap.put(gotPhone, entry.getValue());
            }
        }
        return phoneMap;
    }

}
