package com.es.core.services.cart;

import com.es.core.dao.StockDao;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    private StockDao stockDao;
    private Cart cart;

    @Autowired
    public CartServiceImpl(StockDao stockDao, Cart cart) {
        this.stockDao = stockDao;
        this.cart = cart;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void update(Map<Long, Integer> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        CartItem removableCartItem = cart.getCartItems().stream().filter(cartItem -> cartItem.getPhoneId().equals(phoneId)).findFirst().get();
        cart.getCartItems().remove(removableCartItem);
    }

    @Override
    public Integer getQuantityOfProducts() {
        Integer quantity = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            quantity += cartItem.getQuantity();
        }
        return quantity;
    }

    @Override
    public void addPhone(Long phoneId, Integer quantity) throws OutOfStockException{
        CartItem newCartItem = new CartItem(phoneId, quantity);
        if (cart.getCartItems().contains(newCartItem)) {
            increasePhoneQuantity(cart, phoneId, quantity);
        } else if(isAvailability(phoneId, quantity)) {
            cart.getCartItems().add(newCartItem);
        } else {
            throw new OutOfStockException();
        }
    }

    private void increasePhoneQuantity(Cart cart, Long phoneId, Integer quantity) throws OutOfStockException {
        CartItem cartItem = cart.getCartItems().get(getIndexOf(phoneId, quantity));
        if(isAvailability(phoneId, quantity)) {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        } else {
            throw new OutOfStockException();
        }
    }

    private int getIndexOf(long phoneId, int quantity) {
        return cart.getCartItems().indexOf(new CartItem(phoneId, quantity));
    }

    private boolean isAvailability(Long phoneId, Integer quantity) {
        int stock = stockDao.getStockFor(phoneId);
        int indexOfOldCartItem = getIndexOf(phoneId, quantity);
        if (indexOfOldCartItem >= 0) {
            CartItem oldCartItem = cart.getCartItems().get(indexOfOldCartItem);
            return stock - oldCartItem.getQuantity() >= quantity;
        } else {
            return stock >= quantity;
        }
    }
}
