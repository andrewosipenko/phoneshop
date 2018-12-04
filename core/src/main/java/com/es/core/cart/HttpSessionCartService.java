package com.es.core.cart;

import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockDao;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    //@Resource
    private Cart cart;
    //@Resource
    private StockDao stockDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        CartItem cartItem = new CartItem(phoneId, quantity);
        if (isPossible(phoneId, quantity)) {
            if (cart.getCartItems().contains(cartItem)) {
                long oldCount = cart.getCartItems().get(cart.getCartItems().indexOf(cartItem)).getPhoneQuantity();
                cart.getCartItems().get(cart.getCartItems().indexOf(cartItem)).setPhoneQuantity(oldCount + quantity);
            } else {
                cart.getCartItems().add(cartItem);
            }
        } else {
            throw new OutOfStockException();
        }
    }

    @Override
    public void update(Map<Long, Long> items) throws OutOfStockException {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            CartItem cartItem = new CartItem(item.getKey(), item.getValue());
            if (isPossible(cartItem.getPhoneId(), cartItem.getPhoneQuantity())) {
                if (cart.getCartItems().contains(cartItem)) {
                    cart.getCartItems().set(cart.getCartItems().indexOf(cartItem), cartItem);
                } else {
                    cart.getCartItems().add(cartItem);
                }
            } else {
                throw new OutOfStockException();
            }
        }
    }

    @Override
    public void remove(Long phoneId) {
        CartItem cartItem = new CartItem(phoneId, 0L);
        cart.getCartItems().remove(cartItem);
    }

    private boolean isPossible(Long phoneId, Long quantity) {
        Stock stock = stockDao.get(phoneId).get();
        int stockCount = stock.getStock();
        CartItem cartItem = cart.getCartItems().get(cart.getCartItems().indexOf(new CartItem(phoneId, quantity)));
        return stockCount - cartItem.getPhoneQuantity() >= quantity;
    }
}
