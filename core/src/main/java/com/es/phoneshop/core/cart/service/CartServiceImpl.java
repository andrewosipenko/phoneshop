package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.core.phone.dao.PhoneDao;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.dao.StockDao;
import com.es.phoneshop.core.stock.model.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private StockDao stockDao;
    @Resource
    private Cart cart;

    @Override
    public CartStatus getCartStatus() {
        List<CartItem> items = cart.getItems();
        long phonesTotal = items.stream()
                .reduce(0L, (num, item) -> num + item.getQuantity(), (num1, num2) -> num1 + num2);
        BigDecimal costTotal = items.stream()
                .reduce(BigDecimal.ZERO, (cost, item) -> cost.add(item.getPhone().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))), BigDecimal::add);
        return new CartStatus(phonesTotal, costTotal);
    }

    @Override
    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(cart.getItems());
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        Phone phone = phoneDao.get(phoneId).orElseThrow(NoSuchPhoneException::new);
        Stock stock = stockDao.get(phone).orElseThrow(NoStockFoundException::new);
        if (stock.getStock() < quantity)
            throw new TooBigQuantityException();
        cart.addItem(phone, quantity);
    }

    @Override
    public void update(Map<Long, Long> updateItems) {
        checkUpdateItems(updateItems);
        cart.update(updateItems);
    }

    @Override
    public void checkUpdateItems(Map<Long, Long> updateItems) {
        checkIfAllUpdatedPhonesPresent(new HashSet<>(updateItems.keySet()));
        List<CartItem> cartItems = cart.getItems();
        Set<Long> tooBigQuantityPhoneIds = new HashSet<>();
        for (CartItem item : cartItems) {
            Phone phone = item.getPhone();
            if (!updateItems.containsKey(phone.getId()))
                continue;
            Long quantity = updateItems.get(phone.getId());
            Stock stock = stockDao.get(phone).orElseThrow(NoStockFoundException::new);
            if (stock.getStock() < quantity)
                tooBigQuantityPhoneIds.add(phone.getId());
        }
        if (!tooBigQuantityPhoneIds.isEmpty())
            throw new TooBigQuantityException(tooBigQuantityPhoneIds);
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private void checkIfAllUpdatedPhonesPresent(Set<Long> updatedPhoneIds) {
        Set<Long> cartPhoneIds = cart.getItems().stream()
                .map(item -> item.getPhone().getId())
                .collect(Collectors.toSet());
        updatedPhoneIds.removeAll(cartPhoneIds);
        if (!updatedPhoneIds.isEmpty())
            throw new NoSuchPhoneException();
    }
}
