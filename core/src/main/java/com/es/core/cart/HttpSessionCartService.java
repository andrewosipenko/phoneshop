package com.es.core.cart;

import com.es.core.cart.exception.IllegalPhoneException;
import com.es.core.cart.exception.OutOfStockException;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {

    private static final String QUANTITY_IS_NULL_EXCEPTION_MSG = "Quantity is null";
    private static final String CART_IS_NULL_EXCEPTION_MSG = "Cart is null";
    private static final String QUANTITY_IS_NEGATIVE_EXCEPTION_MSG = "Quantity can't be negative";
    public static final String LIST_CART_ITEMS_IS_NULL_MSG = ": cartItems is null";
    public static final String MAP_CAN_T_BE_NULL_MSG = "Map can't be null";
    @Resource
    private PhoneDao phoneDao;

    @Override
    public void addPhone(Long phoneId, Long quantity, Cart cart) {
        checkValues(quantity, cart);
        removeNulls(cart);
        Stock stock = phoneDao.getStock(phoneId).orElseThrow(() -> new IllegalPhoneException(phoneId));
        checkStock(phoneId, quantity, cart, stock);
        CartItem cartItem = findCartItemById(cart, phoneId).orElse(new CartItem());
        if (cartItem.getQuantity().equals(0L)) {
            cart.getItems().add(cartItem);
        }
        cartItem.setPhone(stock.getPhone());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cart.setTotalPrice(calculateTotalPrice(cart));
        cart.setQuantity(calculateQuantity(cart));
    }

    private void removeNulls(Cart cart) {
        List<CartItem> validCartItems = cart.getItems().stream()
                .filter(cartItem -> cartItem != null
                        && cartItem.getPhone() != null
                        && cartItem.getPhone().getId() != null
                        && cartItem.getQuantity() != null
                        && cartItem.getPhone().getPrice() != null)
                .collect(Collectors.toList());
        cart.setItems(validCartItems);
    }

    public Optional<CartItem> findCartItemById(Cart cart, Long id) {
        CartItem result = null;
        for (CartItem item : cart.getItems()) {
            if (item.getPhone().getId().equals(id)) {
                result = item;
                break;
            }
        }
        return Optional.ofNullable(result);
    }


    private BigDecimal calculateTotalPrice(Cart validCart) {
        return validCart.getItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long calculateQuantity(Cart validCart) {
        return validCart.getItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    private void checkStock(Long phoneId, Long quantity, Cart cart, Stock stock) {
        if (stock.getPhone().getPrice() == null) {
            throw new IllegalPhoneException(stock.getPhone().getModel());
        }
        CartItem cartItem = findCartItemById(cart, phoneId).orElse(new CartItem());
        if (stock.getStock() - stock.getReserved() - quantity - cartItem.getQuantity() < 1) {
            throw new OutOfStockException(stock.getPhone().getModel(), quantity + cartItem.getQuantity());
        }
    }

    private void checkValues(Long quantity, Cart cart) {
        if (quantity == null) {
            throw new IllegalArgumentException(QUANTITY_IS_NULL_EXCEPTION_MSG);
        }
        if (quantity < 1) {
            throw new IllegalArgumentException(QUANTITY_IS_NEGATIVE_EXCEPTION_MSG);
        }
        validateCart(cart);
    }

    private void validateCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException(CART_IS_NULL_EXCEPTION_MSG);
        }
        if (cart.getItems() == null) {
            throw new IllegalArgumentException(CART_IS_NULL_EXCEPTION_MSG + LIST_CART_ITEMS_IS_NULL_MSG);
        }
    }


    @Override
    public void update(Map<Long, Long> validItems, Cart cart) {
        if (validItems == null) {
            throw new IllegalArgumentException(MAP_CAN_T_BE_NULL_MSG);
        }
        removeNulls(cart);
        validItems.forEach((id, quantity) -> {
            Optional<CartItem> cartItem = findCartItemById(cart, id);
            cartItem.ifPresent(item -> item.setQuantity(quantity));
        });
        cart.setTotalPrice(calculateTotalPrice(cart));
        cart.setQuantity(calculateQuantity(cart));
    }

    @Override
    public void remove(Long phoneId, Cart cart) {
        removeNulls(cart);
        cart.setItems(removeCartItemByIdAhdReturnCartItems(phoneId, cart));
        cart.setQuantity(calculateQuantity(cart));
        cart.setTotalPrice(calculateTotalPrice(cart));
    }

    private List<CartItem> removeCartItemByIdAhdReturnCartItems(Long phoneId, Cart cart) {
        return cart.getItems().stream()
                .filter(cartItem -> !cartItem.getPhone().getId().equals(phoneId))
                .collect(Collectors.toList());
    }

    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }
}
