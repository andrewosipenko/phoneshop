package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.form.order.OrderForm;
import com.es.core.form.order.OrderFormItem;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.cart.PriceService;
import com.es.core.service.phone.PhoneService;
import com.es.core.service.stock.StockService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("#{new java.math.BigDecimal(${delivery.price})}")
    private BigDecimal deliveryPrice;
    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private PriceService priceService;
    @Resource
    private StockService stockService;
    @Resource
    private CartService cartService;

    @Override
    public Order createOrder(OrderForm orderForm, Cart cart) {
        Order order = new Order();
        order.setFirstName(orderForm.getFirstName());
        order.setLastName(orderForm.getLastName());
        order.setDeliveryAddress(orderForm.getDeliveryAddress());
        order.setContactPhoneNo(orderForm.getContactPhoneNo());
        order.setAdditionalInformation(orderForm.getAdditionalInformation());
        List<OrderItem> orderItemList = orderItemService.getOrderItemList(cart);
        order.setOrderItems(orderItemList);
        priceService.recalculateOrder(order);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        stockService.updateStocks(order, true);
        order.setStatus(OrderStatus.NEW);
        orderDao.save(order);
        cartService.clearCart();
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
            orderFormItem.setQuantity(cartService.getItemQuantity(phone.getId()));
            orderFormItems.add(orderFormItem);
        }
        return orderFormItems;
    }

    @Override
    public Order getOrder(Long key) {
        return orderDao.get(key).get();
    }

    @Override
    public Order getOrder(String key) {
        return orderDao.get(key).get();
    }

    @Override
    public String getOrderKey(Long orderId){
        return orderDao.getOrderKey(orderId).get();
    }

    @Override
    public void updateOrderForm(OrderForm orderForm, Cart cart) {
        List<OrderFormItem> orderItemsFromCart = getOrderItemsFromCart(cart);
        orderForm.setOrderFormItems(orderItemsFromCart);
        BigDecimal subtotal = cart.getSubtotal();
        orderForm.setSubtotal(subtotal);
        orderForm.setDeliveryPrice(deliveryPrice);
        orderForm.setTotalPrice(subtotal.add(deliveryPrice));
    }

    @Override
    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        orderDao.updateOrderStatus(orderId, orderStatus);
    }
}
