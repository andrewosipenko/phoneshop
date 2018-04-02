package com.es.phoneshop.web.model.cart;

import com.es.core.model.phone.Phone;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class CartList {
    @Valid
    @NotNull
    private List<CartPhone> cartPhones;

    public CartList() { }

    public CartList(Map<Phone, Long> phonesWithQuantity) {
        this.cartPhones = phonesWithQuantity.keySet().stream()
                .map(phone -> new CartPhone(phone.getId(), phonesWithQuantity.get(phone)))
                .collect(toList());
    }

    public List<CartPhone> getCartPhones() {
        return cartPhones;
    }

    public void setCartPhones(List<CartPhone> cartPhones) {
        this.cartPhones = cartPhones;
    }

    public Map<Long, Long> getAllPhonesWithQuantity() {
        return cartPhones.stream()
                .collect(toMap(CartPhone::getPhoneId, CartPhone::getQuantity));
    }
}
