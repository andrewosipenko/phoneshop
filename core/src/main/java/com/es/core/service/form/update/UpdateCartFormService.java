package com.es.core.service.form.update;

import com.es.core.form.cart.updateForm.CartFormItem;
import com.es.core.form.cart.updateForm.UpdateCartForm;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateCartFormService {

    public UpdateCartForm getUpdateCartForm(List<Phone> phones, Map<Long, Long> cartItems){
        UpdateCartForm updateCartForm = new UpdateCartForm();
        List<CartFormItem> cartFormItems = getCartFormItems(phones, cartItems);
        updateCartForm.setCartFormItems(cartFormItems);
        return updateCartForm;
    }

    public List<CartFormItem> getCartFormItems(List<Phone> phones, Map<Long, Long> cartItems){
        List<CartFormItem> cartFormItems = new ArrayList<>();
        for(Phone phone : phones){
            CartFormItem cartFormItem = new CartFormItem();

            cartFormItem.setPhoneId(phone.getId());

            Long quantity = cartItems.get(phone.getId());
            cartFormItem.setQuantity(quantity);

            cartFormItems.add(cartFormItem);
        }
        return cartFormItems;
    }

    public Map<Long, Long> getCartItems(UpdateCartForm updateCartForm){
        Map<Long, Long> cartItems = new HashMap<>();
        for(CartFormItem cartFormItem : updateCartForm.getCartFormItems()){
            Long phoneId = cartFormItem.getPhoneId();
            cartItems.put(phoneId, cartFormItem.getQuantity());
        }
        return cartItems;
    }
}