package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.service.price.PriceService;
import com.es.core.service.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private PriceService priceService;

    @Resource
    private StockService stockService;

    @Autowired(required = false)
    private Cart cart;

    @Override
    public void addCartItem(Cart cart, Long phoneId, Long quantity) {
        List<CartItem> cartItems = cart.getCartItems();
        Phone phone = phoneDao.get(phoneId).orElseThrow(IllegalArgumentException::new);

        Optional<CartItem> optionalCartItem = cartItems.stream()
                .findAny()
                .filter(item -> phoneId.equals(item.getPhone().getId()));

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(quantity, phone);
            cartItems.add(cartItem);
        }

        priceService.recalculatePrice(cart);
    }

    @Override
    public void update(Cart cart, Map<Long, Long> items) {
        List<CartItem> cartItemList = cart.getCartItems();
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            cartItemList.stream()
                    .filter(cartItem ->
                            item.getKey().equals(cartItem.getPhone().getId()))
                    .findAny()
                    .orElseThrow(IllegalArgumentException::new)
                    .setQuantity(item.getValue());
        }

        priceService.recalculatePrice(cart);
    }

    @Override
    public void remove(Cart cart, Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> phoneId.equals(cartItem.getPhone().getId()));
        priceService.recalculatePrice(cart);
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public Map<Long, Long> createMapForUpdating(String[] quantities, List<CartItem> cartItems) {
        Map<Long, Long> map = new HashMap<>();
        for (int i = 0; i < quantities.length; i++) {
            map.put(cartItems.get(i).getPhone().getId(), Long.valueOf(quantities[i]));
        }
        return map;
    }

    @Override
    public void clearCart(Cart cart) {
        cart.setCartItems(new ArrayList<>());
        cart.setTotalPrice(new BigDecimal(0));
    }

    @Override
    public void removeMissingQuantity(CartItem cartItem) {
        Stock stock = stockService.findPhoneStock(cartItem.getPhone().getId());
        if (stock == null) {
            throw new IllegalArgumentException();
        } else {
            Long quantity = stock.getStock();
            cartItem.setQuantity(quantity);
        }
    }
}
