package com.es.core.services.cart;

import com.es.core.dao.PhoneDao;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Stock;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private Cart cart;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void update(Map<Long, Integer> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public synchronized void remove(Long phoneId) {
        CartItem cartItem = new CartItem(phoneId, 0);
        phoneDao.removeReservationFor(phoneId, cart.getCartItems().get(cart.getCartItems().indexOf(cartItem)).getQuantity());
        cart.getCartItems().remove(cartItem);
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
    public BigDecimal getTotalPriceOfProducts() {
        BigDecimal totalCartPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            totalCartPrice = totalCartPrice.add(phoneDao.get(cartItem.getPhoneId()).get().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        return totalCartPrice;
    }

    @Override
    public synchronized void addPhone(Long phoneId, Integer quantity) throws OutOfStockException{
        CartItem newCartItem = new CartItem(phoneId, quantity);
        if (cart.getCartItems().contains(newCartItem)) {
            increasePhoneQuantity(cart, phoneId, quantity);
        } else if(isAvailability(phoneId, quantity)) {
            cart.getCartItems().add(newCartItem);
            phoneDao.makeReservationFor(phoneId, quantity);
        } else {
            throw new OutOfStockException();
        }
    }

    private void increasePhoneQuantity(Cart cart, Long phoneId, Integer quantity) throws OutOfStockException {
        CartItem cartItem = cart.getCartItems().get(cart.getCartItems().indexOf(new CartItem(phoneId, quantity)));
        if(isAvailability(phoneId, quantity)) {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            phoneDao.makeReservationFor(phoneId, quantity);
        } else {
            throw new OutOfStockException();
        }
    }

    private boolean isAvailability(Long phoneId, Integer quantity) {
        Stock stock = phoneDao.getStockFor(phoneId);
        return (stock.getStock() - stock.getReserved() >= quantity);
    }
}
