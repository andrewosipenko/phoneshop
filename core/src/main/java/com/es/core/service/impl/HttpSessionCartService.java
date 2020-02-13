package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.dao.StockDao;
import com.es.core.exception.OutOfStockException;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private Cart cart;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private StockDao stockDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws Exception {
        Phone phoneToAdd = checkPhone(phoneId);

        Optional<Stock> phoneStock = stockDao.getStockById(phoneId);
        if (!phoneStock.isPresent()) {
            throw new PhoneNotFoundException(phoneId);
        }
        Stock phoneToAddStock = phoneStock.get();
        Integer available = phoneToAddStock.getStock() - phoneToAddStock.getReserved();

        Optional<CartItem> optionalCartItem = findOptionalCartItem(phoneId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            Long newQuantity = cartItem.getQuantity() + quantity;
            if (quantity > available) {
                throw new OutOfStockException();
            }
            cartItem.setQuantity(newQuantity);
            stockDao.update(phoneId, Math.toIntExact(newQuantity + phoneToAddStock.getReserved()));
        } else {
            if (quantity > available) {
                throw new OutOfStockException();
            }
            cart.getCartItems().add(new CartItem(phoneToAdd, quantity));
            stockDao.update(phoneId, Math.toIntExact(quantity + phoneToAddStock.getReserved()));
        }
        recalculateTotals();
    }

    private Optional<CartItem> findOptionalCartItem(Long phoneId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findFirst();
    }


    @Override
    public void update(Map<Long, Long> items) throws Exception {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            Optional<CartItem> cartItem = findOptionalCartItem(item.getKey());
            if (cartItem.isPresent()) {
                update(item.getKey(), item.getValue());
            } else {
                addPhone(item.getKey(), item.getValue());
            }
        }
    }

    private void update(Long phoneId, Long quantity) {
        stockDao.update(phoneId, Math.toIntExact(quantity));
    }

    @Override
    public void remove(Long phoneId) {
        Optional<CartItem> cartItemToDelete = findOptionalCartItem(phoneId);
        if (cartItemToDelete.isPresent()) {
            cart.getCartItems().remove(cartItemToDelete.get());
            recalculateTotals();
            Optional<Stock> stock = stockDao.getStockById(phoneId);
            stockDao.update(phoneId, Math.toIntExact(stock.get().getReserved() -
                    cartItemToDelete.get().getQuantity()));
        }
    }

    @Override
    public BigDecimal getCartTotalPrice() {
        return getCart().getTotalPrice();
    }

    @Override
    public Long getCartTotalQuantity() {
        return getCart().getTotalQuantity();
    }

    private Phone checkPhone(Long phoneId) throws PhoneNotFoundException {
        Optional<Phone> phone = phoneDao.get(phoneId);
        if (!phone.isPresent()) {
            throw new PhoneNotFoundException(phoneId);
        }
        Phone phoneToAdd = phone.get();
        if (phoneToAdd.getPrice() == null) {
            throw new IllegalArgumentException();
        }
        return phoneToAdd;
    }

    private void recalculateTotals() {
        cart.setTotalQuantity(cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum());
        cart.setTotalPrice(BigDecimal.valueOf(cart.getCartItems().stream()
                .mapToLong(item -> item.getQuantity() * item.getPhone().getPrice().intValueExact())
                .sum()));
    }
}
