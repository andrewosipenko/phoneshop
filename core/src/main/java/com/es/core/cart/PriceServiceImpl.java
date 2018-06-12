package com.es.core.cart;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceServiceImpl implements PriceService {

    private BigDecimal deliveryPrice;

    @Value("${delivery.price}")
    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    @Override
    public BigDecimal obtainCartSubtotal(Cart cart) {
        return cart.getProducts().values().stream()
                .map(CartEntry::obtainCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal obtainCartTotal(Cart cart) {
        return obtainCartSubtotal(cart).add(getDeliveryPrice());
    }

    @Override
    public void updateOrderPrice(Order order){
        BigDecimal subtotal = obtainOrderSubtotal(order);

        order.setDeliveryPrice(getDeliveryPrice());
        order.setSubtotal(subtotal);
        order.setTotalPrice(subtotal.add(getDeliveryPrice()));
    }

    private BigDecimal obtainOrderSubtotal(Order order){
        return order.getOrderItems().stream()
                .map(this::obtainOrderItemCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal obtainOrderItemCost(OrderItem item){
        return BigDecimal.valueOf(item.getQuantity())
                .multiply(item.getPhone().getPrice());
    }
}
