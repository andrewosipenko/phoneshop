package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.OutOfStockException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.phone.service.PhoneService;
import com.es.phoneshop.core.stock.model.Stock;
import com.es.phoneshop.core.stock.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private PhoneService phoneService;
    @Resource
    private StockService stockService;
    @Resource
    private Cart cart;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Long phoneId, Long quantity) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        Phone phone = phoneService.getPhone(phoneId).orElseThrow(NoSuchPhoneException::new);
        Stock stock = stockService.getStock(phone).orElseThrow(NoStockFoundException::new);
        Optional<CartItem> item = cart.getCartItems().stream()
                .filter(cartRecord -> cartRecord.getPhone().getId().equals(phone.getId()))
                .findFirst();
        if (stock.getStock() < quantity + (item.isPresent() ? item.get().getQuantity() : 0))
            throw new TooBigQuantityException();
        if (item.isPresent())
            item.get().setQuantity(item.get().getQuantity() + quantity);
        else
            cart.getCartItems().add(new CartItem(phone, quantity));
        recountSubtotal();
    }

    @Override
    public void update(Map<Long, Long> updateItems) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        checkUpdateRecords(updateItems);
        for (CartItem item : cart.getCartItems()) {
            Phone phone = item.getPhone();
            if (!updateItems.containsKey(phone.getId()))
                continue;
            Long quantity = updateItems.get(phone.getId());
            item.setQuantity(quantity);
        }
        recountSubtotal();
    }

    private void checkUpdateRecords(Map<Long, Long> updateItems) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        checkIfAllUpdatedPhonesPresent(new HashSet<>(updateItems.keySet()));
        List<CartItem> cartItems = cart.getCartItems();
        Set<Long> tooBigQuantityPhoneIds = new HashSet<>();
        for (CartItem item : cartItems) {
            Phone phone = item.getPhone();
            if (!updateItems.containsKey(phone.getId()))
                continue;
            Long quantity = updateItems.get(phone.getId());
            Stock stock = stockService.getStock(phone).orElseThrow(NoStockFoundException::new);
            if (stock.getStock() < quantity)
                tooBigQuantityPhoneIds.add(phone.getId());
        }
        if (!tooBigQuantityPhoneIds.isEmpty())
            throw new TooBigQuantityException(tooBigQuantityPhoneIds);
    }

    private void checkIfAllUpdatedPhonesPresent(Set<Long> updatedPhoneIds) {
        Set<Long> cartPhoneIds = cart.getCartItems().stream()
                .map(item -> item.getPhone().getId())
                .collect(Collectors.toSet());
        updatedPhoneIds.removeAll(cartPhoneIds);
        if (!updatedPhoneIds.isEmpty())
            throw new NoSuchPhoneException();
    }

    @Override
    public void remove(Long phoneId) throws NoSuchPhoneException {
        Set<Long> cartPhoneIds = cart.getCartItems().stream()
                .map(item -> item.getPhone().getId())
                .collect(Collectors.toSet());
        if (!cartPhoneIds.contains(phoneId))
            throw new NoSuchPhoneException();
        cart.getCartItems().removeIf(item -> Objects.equals(item.getPhone().getId(), phoneId));
        recountSubtotal();
    }

    @Override
    public void clear() {
        cart.getCartItems().clear();
        recountSubtotal();
    }

    @Override
    public void validateStocksAndRemoveOdd() throws OutOfStockException {
        List<Phone> rejectedPhones = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            Stock stock = stockService.getStock(item.getPhone()).orElseThrow(NoStockFoundException::new);
            if (stock.getStock() < item.getQuantity())
                rejectedPhones.add(item.getPhone());
        }
        for (Phone phone : rejectedPhones)
            remove(phone.getId());
        recountSubtotal();
        if (!rejectedPhones.isEmpty())
            throw new OutOfStockException(rejectedPhones);
    }

    private void recountSubtotal() {
        BigDecimal subtotal = cart.getCartItems().stream()
                .reduce(BigDecimal.ZERO, (cost, item) -> cost.add(item.getPhone().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))), BigDecimal::add);
        cart.setSubtotal(subtotal);
    }
}
