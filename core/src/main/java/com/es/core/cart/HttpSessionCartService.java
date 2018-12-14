package com.es.core.cart;

import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockDao;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private Cart cart;
    @Resource
    private StockDao stockDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        CartItem cartItem = new CartItem(phoneId, quantity);
        if (isPossible(phoneId, quantity)) {
            if (cart.getCartItems().contains(cartItem)) {
                int indexCartItem = cart.getCartItems().indexOf(cartItem);
                long oldCount = cart.getCartItems().get(indexCartItem).getPhoneQuantity();
                cart.getCartItems().get(indexCartItem).setPhoneQuantity(oldCount + quantity);
            } else {
                cart.getCartItems().add(cartItem);
            }
        } else {
            throw new OutOfStockException();
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            CartItem cartItem = new CartItem(item.getKey(), item.getValue());
            if (isPossible(cartItem.getPhoneId(), cartItem.getPhoneQuantity())) {
                if (cart.getCartItems().contains(cartItem)) {
                    int indexCartItem = cart.getCartItems().indexOf(cartItem);
                    cart.getCartItems().set(indexCartItem, cartItem);
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
        int indexCartItem = cart.getCartItems().indexOf(new CartItem(phoneId, quantity));
        CartItem cartItem = cart.getCartItems().get(indexCartItem);
        return stockCount - cartItem.getPhoneQuantity() >= quantity;
    }
}
