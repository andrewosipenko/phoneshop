package com.es.core.service.cart;

import com.es.core.form.cart.UpdateCartForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Map;

public interface CartService {
    Cart getCart();
    void addPhone(Long phoneId, Long quantity);
    void update(Map<Long, Long> items);
    void remove(Long phoneId);
    UpdateCartForm getUpdateCart(List<Phone> phones, Map<Long, Long> cartItems);
    Map<Long, Long> getItemsCart(UpdateCartForm updateCartForm);
    boolean removePhonesOutOfTheStock();
    void clearCart();
}
