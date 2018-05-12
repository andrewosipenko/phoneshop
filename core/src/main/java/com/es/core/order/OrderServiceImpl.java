package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.model.stock.StockDao;
import javafx.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private StockDao stockDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private CartService cartService;

    public OrderServiceImpl() {
    }

    @Override
    public Order createOrder(Cart cart) throws PhoneInCartNotFoundException, OutOfStockException {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString().substring(0, 13));
        order.setStatus(OrderStatus.NEW);
        order.setDeliveryPrice(cart.getDeliveryPrice());
        order.setSubtotal(cart.getCost());
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        setOrderItems(cart, order);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException, EmptyOrderListException, PhoneInCartNotFoundException {
        if(order == null || CollectionUtils.isEmpty(order.getOrderItems())) {
            throw new EmptyOrderListException();
        }
        Cart cart = cartService.getCart();
        List<Stock> validatedStocks = validateCart(cart);
        setOrderItems(cart, order);
        stockDao.updateStocks(validatedStocks);
        orderDao.save(order);
        cartService.clearCart();
    }

    @Override
    public List<Stock> validateCart(Cart cart) throws PhoneInCartNotFoundException, OutOfStockException {
        List<CartItem> beforeValidationItems = new ArrayList<>(cart.getItems());
        Pair<List<Stock>, List<Phone>> validatedStocksAndRejectedPhones = validateAndReturnRejected(cart);
        if(beforeValidationItems.size() > cart.getItems().size()) {
            cartService.recalculateCartCost();
            throw new OutOfStockException(validatedStocksAndRejectedPhones.getValue());
        }
        return validatedStocksAndRejectedPhones.getKey();
    }

    private Pair<List<Stock>, List<Phone>> validateAndReturnRejected(Cart cart) throws PhoneInCartNotFoundException {
        List<CartItem> afterValidationOrderItems = new ArrayList<>(cart.getItems());
        List<CartItem> orderItems = new ArrayList<>(cart.getItems());
        List<Long> orderItemsIds = orderItems
                .stream()
                .map(CartItem::getPhone)
                .map(Phone::getId)
                .collect(Collectors.toList());
        List<Phone> rejectedPhones = new ArrayList<>();
        List<Stock> validStocks = new ArrayList<>();
        Map<Long, Stock> stocks = stockDao.getStocks(orderItemsIds);
        for(CartItem orderItem : orderItems) {
            Stock phoneStock = new Stock(stocks.get(orderItem.getPhone().getId()));
            Integer stock = phoneStock.getStock();
            Long quantity = orderItem.getQuantity();
            if(stock - quantity.intValue() < 0) {
                afterValidationOrderItems.remove(orderItem);
                cartService.remove(orderItem.getPhone().getId());
                rejectedPhones.add(orderItem.getPhone());
            } else {
               phoneStock.setStock(stock - quantity.intValue());
               phoneStock.setReserved(quantity.intValue() + phoneStock.getReserved());
               validStocks.add(phoneStock);
            }
        }
        cart.setItems(afterValidationOrderItems);
        if(afterValidationOrderItems.size() < orderItems.size()) {
            List<CartItem> newCartItems = new ArrayList<>();
            afterValidationOrderItems.forEach(e ->
                newCartItems.add(new CartItem(e.getPhone(), e.getQuantity())));
            cartService.getCart().setItems(newCartItems);
            cartService.recalculateCartCost();
        }
        return new Pair<>(new ArrayList<>(validStocks), rejectedPhones);
    }

    private void setOrderItems(Cart cart, Order order) {
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity(),
                        cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))))
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
    }
}
