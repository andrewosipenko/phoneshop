package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.order.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class PriceServiceImpl  implements PriceService {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private OrderService orderService;

    @Override
    public void recalculateCart(Cart cart) {
        BigDecimal newSubtotal = BigDecimal.ZERO;
        Long newAmount = 0L;
        for (Map.Entry<Long, Long> item : cart.getCartItems().entrySet()) {
            Long phoneId = item.getKey();
            Long quantity = item.getValue();
            Phone phone = phoneDao.get(phoneId).get();
            newSubtotal = newSubtotal.add(phone.getPrice().multiply(BigDecimal.valueOf(quantity)));
            newAmount += quantity;
        }
        cart.setSubtotal(newSubtotal);
        cart.setCartAmount(newAmount);
    }

    @Override
    public void recalculateOrder(Order order) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal deliveryPrice = orderService.getDeliveryPrice();
        for (OrderItem orderItem : order.getOrderItems()) {
            BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());
            BigDecimal price = orderItem.getPhone().getPrice();
            subtotal = subtotal.add(quantity.multiply(price));
        }
        order.setSubtotal(subtotal);
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(subtotal.add(deliveryPrice));
    }
}
