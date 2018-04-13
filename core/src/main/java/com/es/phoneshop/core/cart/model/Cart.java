package com.es.phoneshop.core.cart.model;

import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.model.Stock;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = "${cart.scope}", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(Phone phone, Long quantity) {
        Optional<CartItem> item = items.stream()
                .filter(cartItem -> cartItem.getPhone().getId().equals(phone.getId()))
                .findFirst();
        if (item.isPresent())
            item.get().setQuantity(item.get().getQuantity() + quantity);
        else
            items.add(new CartItem(phone, quantity));
    }

    public void update(Map<Long, Long> updateItems) {
        for (CartItem item : items) {
            Phone phone = item.getPhone();
            if (!updateItems.containsKey(phone.getId()))
                continue;
            Long quantity = updateItems.get(phone.getId());
            item.setQuantity(quantity);
        }
    }

    public void remove(Long phoneId) {
        items.removeIf(item -> Objects.equals(item.getPhone().getId(), phoneId));
    }
}
