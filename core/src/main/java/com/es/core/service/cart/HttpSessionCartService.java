package com.es.core.service.cart;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.exceptions.ProductNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.cart.InfoCart;
import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneStockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private PhoneStockService phoneStockService;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;


    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Phone phoneToAdd = phoneDao.get(phoneId).orElseThrow(ProductNotFoundException::new);
        Long currentQuantity = cart.getCartItems().stream().filter(cartItem -> cartItem.getPhone().getId().equals(phoneId))
                .findFirst().map(CartItem::getQuantity).orElse(0L);
        if (!phoneStockService.hasEnoughStock(phoneId, currentQuantity + quantity)) {
            throw new OutOfStockException();
        }
        if (currentQuantity != 0L) {
            cart.getCartItems().stream().filter(cartItem -> cartItem.getPhone().getId().equals(phoneId))
                    .findFirst().ifPresent(cartItem -> cartItem.setQuantity(currentQuantity + quantity));
        } else {
            CartItem cartItemToAdd = new CartItem(phoneToAdd, phoneId);
            cart.getCartItems().add(cartItemToAdd);
        }
        recalculateCartPrice(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            cart.getCartItems().stream().filter(cartItem -> item.getKey().equals(cartItem.getPhone().getId()))
                    .findFirst().ifPresent(cartItem -> cartItem.setQuantity(item.getValue()));
        }
        recalculateCartPrice(cart);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> phoneId.equals(cartItem.getPhone().getId()));
        recalculateCartPrice(cart);
    }

    @Override
    public Map<Long, Long> formMapForUpdate(Map<Long, String> cartItems) {
        Map<Long, Long> newMap = new HashMap<>();
        cartItems.forEach((key, value) -> newMap.put(key, Long.valueOf(value)));
        return newMap;
    }

    @Override
    public InfoCart getInfoCart() {
        return new InfoCart(cart.getCartItems().stream().mapToLong(CartItem::getQuantity).sum(), cart.getSubtotalPrice());
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public void clearCart() {
        cart = new Cart();
    }

    @Override
    public void recalculateCartPrice(Cart cart) {
        BigDecimal newSubtotalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        cart.setSubtotalPrice(newSubtotalPrice);
        BigDecimal totalPrice = deliveryPrice.add(newSubtotalPrice);
        cart.setDeliveryPrice(deliveryPrice);
        cart.setTotalPrice(totalPrice);
    }
}
