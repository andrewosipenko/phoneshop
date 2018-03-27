package com.es.core.cart;

import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private Cart cart;

    @Autowired
    private PhoneDao phoneDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    /**
     * @value cartItem is not present only if we haven't already added such phone in a cart.
     * In that case we have to add a new CartItem. In other case we have to update
     * existing CartItem in a cart.
     */
    @Override
    public void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException {

        Optional<Phone> phone = phoneDao.get(phoneId);
        if(phone.isPresent()) {
            CartItem cartItem = new CartItem(phone.get(), quantity);
            Optional<CartItem> itemInCart = getItemFromCart(cartItem);
            List<CartItem> items = cart.getItems();
            if(itemInCart.isPresent()) {
                items.remove(itemInCart.get());
                cartItem.setQuantity(cartItem.getQuantity() + itemInCart.get().getQuantity());
            }
                items.add(cartItem);
                cart.setItems(items);
                cart.setCost(updateAndReturnCost());
        } else {
            throw new PhoneNotFoundException();
        }
    }

    /**
     * @value newCost is always present for now (in case the implementation of HttpSessionCartService
     * doesn't support removing items from cart for now. Method is to be updated.
     */
    private BigDecimal updateAndReturnCost() {
        List<CartItem> items = cart.getItems();
        Optional<BigDecimal> newCost = items
                .stream()
                .map(CartItem::getCartItemCost)
                .reduce(BigDecimal::add);
        if(newCost.isPresent())
        return newCost.get();
        else return cart.getCost();
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private Optional<CartItem> getItemFromCart(CartItem cartItem) {
        return cart.getItems()
                .stream().
                filter(e -> e.getPhone().getId().equals(cartItem.getPhone().getId()))
                .findFirst();
    }
}
