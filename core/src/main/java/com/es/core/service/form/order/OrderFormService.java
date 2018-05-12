package com.es.core.service.form.order;

import com.es.core.cart.Cart;
import com.es.core.form.order.OrderForm;
import com.es.core.form.order.OrderFormItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.order.OrderService;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderFormService {
    @Resource
    private OrderService orderService;
    @Resource
    private PhoneService phoneService;

    public OrderForm createOrderForm(Cart cart){
        OrderForm orderForm = new OrderForm();
        updateOrderForm(orderForm, cart);
        return orderForm;
    }

    public void updateOrderForm(OrderForm orderForm, Cart cart){
        orderForm.setOrderFormItems(getOrderFormItems(cart));
        BigDecimal subtotal = cart.getSubtotal();
        BigDecimal deliveryPrice = orderService.getDeliveryPrice();
        orderForm.setSubtotal(subtotal);
        orderForm.setDeliveryPrice(deliveryPrice);
        orderForm.setTotalPrice(subtotal.add(deliveryPrice));
    }

    private List<OrderFormItem> getOrderFormItems(Cart cart){
        List<Phone> phones = phoneService.getPhonesFromCart(cart);
        List<OrderFormItem> orderFormItems = new ArrayList<>();
        for(Phone phone : phones){
            OrderFormItem orderFormItem = new OrderFormItem();
            orderFormItem.setPhone(phone);
            orderFormItem.setQuantity(cart.getItemQuantity(phone.getId()));

            orderFormItems.add(orderFormItem);
        }
        return orderFormItems;
    }
}
