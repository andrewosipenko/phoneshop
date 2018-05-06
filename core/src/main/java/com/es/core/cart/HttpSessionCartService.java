package com.es.core.cart;

import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException {
        Optional<Phone> phone = phoneDao.get(phoneId);
        if(phone.isPresent()) {
            CartItem cartItem = new CartItem(phone.get(), quantity);
            Optional<CartItem> itemInCart = getItemFromCart(cartItem.getPhone().getId());
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

    @Override
    public void update(Map<Long, Long> items) throws PhoneInCartNotFoundException {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            onUpdate(item.getKey(), item.getValue());
        }
        cart.setCost(updateAndReturnCost());
    }

    @Override
    public void remove(Long phoneId) throws PhoneInCartNotFoundException {
        Optional<CartItem> cartItem = getItemFromCart(phoneId);
        if(cartItem.isPresent()) {
            deleteCartItemById(phoneId);
            cart.setCost(updateAndReturnCost());
        } else {
            throw new PhoneInCartNotFoundException();
        }
    }

    @Override
    public void clearCart() {
        cart.getItems().clear();
        cart.setCost(BigDecimal.ZERO);
    }

    @Override
    public void recalculateCartCost() {
        updateAndReturnCost();
    }

    private BigDecimal updateAndReturnCost() {
        List<CartItem> items = cart.getItems();
        Optional<BigDecimal> newCost = items
                .stream()
                .map(CartItem::getCartItemCost)
                .reduce(BigDecimal::add);
        return newCost.orElse(BigDecimal.ZERO);
    }

    private Optional<CartItem> getItemFromCart(Long id) {
        return cart.getItems()
                .stream().
                filter(e -> e.getPhone().getId().equals(id))
                .findFirst();
    }

    private void deleteCartItemById(Long phoneId) {
        cart.getItems()
                .removeIf((e) -> e.getPhone().getId().equals(phoneId));
    }

    private void onUpdate(Long phoneId, Long quantity) throws PhoneInCartNotFoundException {
       Optional<CartItem> cartItem = getItemFromCart(phoneId);
       CartItem updatedCartItem = cartItem.orElseThrow(PhoneInCartNotFoundException::new);
       cart.getItems().get(cart.getItems().indexOf(updatedCartItem)).setQuantity(quantity);
    }
}
