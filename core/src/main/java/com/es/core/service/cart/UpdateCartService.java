package com.es.core.service.cart;

import com.es.core.form.cart.AddCartForm;
import com.es.core.form.cart.UpdateCartForm;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateCartService {

    public UpdateCartForm getUpdateCart(List<Phone> phones, Map<Long, Long> cartItems) {
        UpdateCartForm updateCartForm = new UpdateCartForm();
        List<AddCartForm> cartFormList = new ArrayList<>();
        for (Phone phone : phones) {
            AddCartForm addCartForm = new AddCartForm();
            Long phoneId = phone.getId();
            addCartForm.setPhoneId(phoneId);
            Long quantity = cartItems.get(phoneId);
            addCartForm.setQuantity(quantity);
            cartFormList.add(addCartForm);
        }
        updateCartForm.setCartFormList(cartFormList);
        return updateCartForm;
    }


    public Map<Long, Long> getItemsCart(UpdateCartForm updateCartForm) {
        Map<Long, Long> cartItems = new HashMap<>();
        for (AddCartForm addCartForm : updateCartForm.getCartFormList()) {
            cartItems.put(addCartForm.getPhoneId(), addCartForm.getQuantity());
        }
        return cartItems;
    }
}
