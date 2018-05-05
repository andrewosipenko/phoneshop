package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.model.stock.StockDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("#{new java.math.BigDecimal(${delivery.price})}")
    private BigDecimal deliveryPrice;

    @Resource
    private StockDao stockDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private CartService cartService;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setDeliveryPrice(deliveryPrice);
        order.setSubtotal(cart.getCost());
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity(),
                        cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))))
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException, EmptyOrderListException, PhoneInCartNotFoundException {
        if(order.getOrderItems().size() == 0) {
            throw new EmptyOrderListException();
        }
        List<OrderItem> beforeValidationItems = order.getOrderItems();
        List<Stock> stocksForUpdate = getValidatedStocks(order);
        if(beforeValidationItems.size() > order.getOrderItems().size())
            throw new OutOfStockException();
        orderDao.save(order);
        stockDao.updateStocks(stocksForUpdate);
        cartService.clearCart();
    }

    private List<Stock> getValidatedStocks(Order possibleOutOfStockOrder) throws PhoneInCartNotFoundException {
        List<OrderItem> afterValidationOrderItems = new ArrayList<>(possibleOutOfStockOrder.getOrderItems());
        List<OrderItem> orderItems = possibleOutOfStockOrder.getOrderItems();
        List<Long> orderItemsIds = orderItems
                .stream()
                .map(OrderItem::getPhone)
                .map(Phone::getId)
                .collect(Collectors.toList());
        Map<Long, Stock> stocks = stockDao.getStocks(orderItemsIds);
        List<Stock> stocksForUpdate = new ArrayList<>();
        for(OrderItem orderItem : orderItems) {
            Stock phoneStock = stocks.get(orderItem.getPhone().getId());
            Integer stock = phoneStock.getStock();
            Long quantity = orderItem.getQuantity();
            if(stock - quantity.intValue() < 0) {
                afterValidationOrderItems.remove(orderItem);
                cartService.remove(orderItem.getPhone().getId());
            } else {
               phoneStock.setStock(stock - quantity.intValue());
               phoneStock.setReserved(quantity.intValue() + phoneStock.getReserved());
               stocksForUpdate.add(phoneStock);
            }
        }
        possibleOutOfStockOrder.setOrderItems(afterValidationOrderItems);
        return stocksForUpdate;
    }
}
