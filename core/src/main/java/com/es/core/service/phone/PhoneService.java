package com.es.core.service.phone;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {
    Optional<Phone> getPhone(Long key);
    void save(Phone phone);
    void delete(Phone phone);
    List<Phone> getPhoneListFromCart(Cart cart);
    List<Phone> getPhoneListFromOrder(Order order);
}
