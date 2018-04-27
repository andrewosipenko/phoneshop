package com.es.core.cart;

import com.es.core.model.phone.exception.NoSuchPhoneException;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class Cart {
    private Map<Long, Long> items;
    private BigDecimal subtotal;
    private Long itemsAmount;

    public Cart(){
        items = new HashMap<>();
        subtotal = BigDecimal.ZERO;
        itemsAmount = 0L;
    }

    public void addPhone(Long id, Long quantity){
        items.merge(id, quantity, (oldQuantity, newQuantity) -> oldQuantity + newQuantity);
    }

    public Map<Long, Long> getItems(){
        return items;
    }

    public BigDecimal getSubtotal(){
        return subtotal;
    }

    public Long getItemsAmount(){
        return itemsAmount;
    }

    public Long getItemQuantity(Long phoneId){ // вынести
        if(!items.containsKey(phoneId)){
            throw new NoSuchPhoneException();
        }
        return items.get(phoneId);
    }

    public void setItemsAmount(Long itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}