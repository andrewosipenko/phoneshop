package com.es.core.service.order;

import com.es.core.form.order.OrderForm;
import com.es.core.form.order.OrderFormItem;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Override
    public Order createOrder(Cart cart) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public OrderForm createOrderForm(Cart cart) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderFormItems(getOrderItemsFromCart(cart));
        BigDecimal subtotal = cart.getSubtotal();
        orderForm.setSubtotal(subtotal);
        orderForm.setDeliveryPrice(deliveryPrice);
        orderForm.setTotalPrice(subtotal.add(deliveryPrice));
        return orderForm;
    }

    private List<OrderFormItem> getOrderItemsFromCart(Cart cart) {
        List<Phone> phones = phoneService.getPhoneListFromCart(cart);
        List<OrderFormItem> orderFormItems = new ArrayList<>();
        for (Phone phone : phones) {
            OrderFormItem orderFormItem = new OrderFormItem();
            orderFormItem.setPhone(phone);
            orderFormItem.setQuantity(cart.getItemQuantity(phone.getId()));
            orderFormItems.add(orderFormItem);
        }
        return orderFormItems;
    }
}
