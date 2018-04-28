package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.model.CartRecord;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
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
    public CartStatus getStatus() {
        List<CartRecord> records = cart.getRecords();
        long phonesTotal = records.stream()
                .reduce(0L, (num, item) -> num + item.getQuantity(), (num1, num2) -> num1 + num2);
        return new CartStatus(phonesTotal, cart.getTotal());
    }

    @Override
    public List<CartRecord> getRecords() {
        return Collections.unmodifiableList(cart.getRecords());
    }

    @Override
    public void add(Long phoneId, Long quantity) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        Phone phone = phoneService.getPhone(phoneId).orElseThrow(NoSuchPhoneException::new);
        Stock stock = stockService.getStock(phone).orElseThrow(NoStockFoundException::new);
        if (stock.getStock() < quantity)
            throw new TooBigQuantityException();
        Optional<CartRecord> item = cart.getRecords().stream()
                .filter(cartRecord -> cartRecord.getPhone().getId().equals(phone.getId()))
                .findFirst();
        if (item.isPresent())
            item.get().setQuantity(item.get().getQuantity() + quantity);
        else
            cart.getRecords().add(new CartRecord(phone, quantity));
        recountTotal();
    }

    @Override
    public void update(Map<Long, Long> updateItems) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        checkUpdateRecords(updateItems);
        for (CartRecord item : cart.getRecords()) {
            Phone phone = item.getPhone();
            if (!updateItems.containsKey(phone.getId()))
                continue;
            Long quantity = updateItems.get(phone.getId());
            item.setQuantity(quantity);
        }
        recountTotal();
    }

    private void checkUpdateRecords(Map<Long, Long> updateItems) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        checkIfAllUpdatedPhonesPresent(new HashSet<>(updateItems.keySet()));
        List<CartRecord> cartRecords = cart.getRecords();
        Set<Long> tooBigQuantityPhoneIds = new HashSet<>();
        for (CartRecord item : cartRecords) {
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

    @Override
    public void remove(Long phoneId) throws NoSuchPhoneException {
        Set<Long> cartPhoneIds = cart.getRecords().stream()
                .map(item -> item.getPhone().getId())
                .collect(Collectors.toSet());
        if (!cartPhoneIds.contains(phoneId))
            throw new NoSuchPhoneException();
        cart.getRecords().removeIf(item -> Objects.equals(item.getPhone().getId(), phoneId));
        recountTotal();
    }

    private void checkIfAllUpdatedPhonesPresent(Set<Long> updatedPhoneIds) {
        Set<Long> cartPhoneIds = cart.getRecords().stream()
                .map(item -> item.getPhone().getId())
                .collect(Collectors.toSet());
        updatedPhoneIds.removeAll(cartPhoneIds);
        if (!updatedPhoneIds.isEmpty())
            throw new NoSuchPhoneException();
    }

    private void recountTotal() {
        List<CartRecord> records = cart.getRecords();
        BigDecimal total = records.stream()
                .reduce(BigDecimal.ZERO, (cost, item) -> cost.add(item.getPhone().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))), BigDecimal::add);
        cart.setTotal(total);
    }
}
