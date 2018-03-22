package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * @throws IllegalStateException - try to add a phone without price
     */
    @Override
    public void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException {
        Phone addedPhone = phoneDao.get(phoneId).orElseThrow(PhoneNotFoundException::new);
        BigDecimal phonePrice = addedPhone.getPrice();
        if (phonePrice == null) {
            throw new IllegalStateException();
        }

        Optional<CartItem> cartItemOptional = findCartItemById(phoneId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(addedPhone, quantity);
            cart.getItems().add(cartItem);
        }

        recalculateCartCost();
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            updateOrDelete(item.getKey(), item.getValue());
        }
    }

    @Override
    public void updateOrDelete(Long phoneId, Long quantity) {
        if (quantity.compareTo(0L) > 0) {
            Optional<CartItem> cartItemOptional = findCartItemById(phoneId);
            if (cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                cartItem.setQuantity(quantity);
            }
        } else {
            cart.getItems().removeIf(e -> phoneId.equals(e.getPhone().getId()));
        }
        recalculateCartCost();
    }

    @Override
    public void deleteOutOfStock() {
        List<Long> phoneIdList = cart.getItems().stream()
                .map(cartItem -> cartItem.getPhone().getId())
                .collect(Collectors.toList());

        List<Stock> stocks = stockDao.getStocks(phoneIdList);
        Map<Long, Stock> stockMap = stocks.stream()
                .collect(Collectors.toMap(Stock::getPhoneId, o -> o));

        Map<Long, Long> availableCountProduct = cart.getItems().stream()
                .collect(Collectors.toMap(cartItem -> cartItem.getPhone().getId(),
                        cartItem -> availableCount(cartItem, stockMap)));

        update(availableCountProduct);
    }

    @Override
    public void clearCart() {
        cart.setItems(new ArrayList<>());
        recalculateCartCost();
    }

    private Long availableCount(CartItem cartItem, Map<Long, Stock> stockMap) {
        Stock stock = stockMap.get(cartItem.getPhone().getId());
        Long availableCount = (long) stock.getStock() - stock.getReserved();
        return Math.min(availableCount, cartItem.getQuantity());
    }

    private Optional<CartItem> findCartItemById(Long phoneId) {
        return cart.getItems().stream().filter(e -> phoneId.equals(e.getPhone().getId()))
                .findFirst();
    }

    private void recalculateCartCost() {
        BigDecimal cartCost = cart.getItems().stream()
                .map(e -> e.getPhone().getPrice().multiply(new BigDecimal(e.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setCost(cartCost);
    }
}
