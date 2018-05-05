package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.model.CartRecord;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.order.dao.OrderDao;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.model.OrderItem;
import com.es.phoneshop.core.order.model.OrderStatus;
import com.es.phoneshop.core.order.throwable.EmptyCartPlacingOrderException;
import com.es.phoneshop.core.order.throwable.OutOfStockException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.model.Stock;
import com.es.phoneshop.core.stock.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;
    @Resource
    private StockService stockService;
    @Resource
    private OrderDao orderDao;
    @Override
    public Order createOrder(List<CartRecord> cartRecords, BigDecimal subtotal) {
        Order order = new Order();
        List<OrderItem> orderItems = cartRecords.stream()
                .map(cartRecord -> {
                    OrderItem item = new OrderItem();
                    item.setQuantity(cartRecord.getQuantity());
                    item.setPhone(cartRecord.getPhone());
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        order.setSubtotal(subtotal);
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(subtotal.add(deliveryPrice));
        return order;
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        return orderDao.get(id);
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException, NoStockFoundException, EmptyCartPlacingOrderException {
        if (order.getOrderItems().isEmpty())
            throw new EmptyCartPlacingOrderException();
        validateStocks(order);
        for (OrderItem item : order.getOrderItems()) {
            Stock stock = stockService.getStock(item.getPhone()).orElseThrow(NoStockFoundException::new);
            stock.setReserved(stock.getReserved() + item.getQuantity());
            stock.setStock(stock.getStock() - item.getQuantity());
            stockService.update(stock);
        }
        order.setStatus(OrderStatus.NEW);
        order.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        orderDao.save(order);
    }

    private void validateStocks(Order order) throws OutOfStockException, NoStockFoundException {
        List<Phone> rejectedPhones = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            Stock stock = stockService.getStock(item.getPhone()).orElseThrow(NoStockFoundException::new);
            if (stock.getStock() < item.getQuantity())
                rejectedPhones.add(item.getPhone());
        }
        if (!rejectedPhones.isEmpty())
            throw new OutOfStockException(rejectedPhones);
    }
}
