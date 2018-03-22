package com.es.core.service.order;

import com.es.core.AbstractTest;
import com.es.core.dao.order.OrderDao;
import com.es.core.exception.OrderNotFoundException;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.cart.CartService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest extends AbstractTest {

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    @Mock
    private OrderDao orderDao;

    @Mock
    private CartService cartService;

    private BigDecimal deliveryPrice = new BigDecimal(5);

    @Before
    public void initDeliveryPrice() {
        ReflectionTestUtils.setField(orderService, "deliveryPrice", deliveryPrice);
    }

    @After
    public void resetPhoneMockDao() {
        try {
            verifyNoMoreInteractions(orderDao);
        } finally {
            reset(orderDao);
        }

        try {
            verifyNoMoreInteractions(cartService);
        } finally {
            reset(cartService);
        }
    }

    @Test
    public void placeOrderCheck() throws OutOfStockException {
        Order order = new Order();
        orderService.placeOrder(order);
        verify(orderDao).save(eq(order));
        verify(cartService).clearCart();
    }

    @Test(expected = OutOfStockException.class)
    public void placeOrderOutOfStock() throws OutOfStockException {
        Order order = new Order();
        doThrow(OutOfStockException.class).when(orderDao).save(order);
        try {
            orderService.placeOrder(order);
        } finally {
            verify(orderDao).save(eq(order));
        }
    }

    @Test
    public void createOrderCheck() {
        final BigDecimal COST = new BigDecimal(1000);
        Cart cart = new Cart();
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(phoneList.get(0), 1L));
        items.add(new CartItem(phoneList.get(1), 2L));
        items.add(new CartItem(phoneList.get(2), 3L));
        cart.setItems(items);
        cart.setCost(COST);

        Order order = orderService.createOrder(cart);

        assertTrue(order.getSubtotal().compareTo(COST) == 0);
        assertTrue(order.getDeliveryPrice().compareTo(deliveryPrice) == 0);
        assertTrue(order.getTotalPrice().compareTo(COST.add(deliveryPrice)) == 0);

        List<CartItem> settedItems = order.getOrderItems().stream()
                .map(orderItem -> new CartItem(orderItem.getPhone(), orderItem.getQuantity()))
                .collect(Collectors.toList());

        assertEquals(items, settedItems);
    }

    @Test
    public void createOrderFromEmptyCart() {
        final BigDecimal COST = BigDecimal.ZERO;
        Cart cart = new Cart();
        List<CartItem> items = new ArrayList<>();
        cart.setItems(items);
        cart.setCost(COST);

        Order order = orderService.createOrder(cart);

        assertTrue(order.getSubtotal().compareTo(COST) == 0);
        assertTrue(order.getDeliveryPrice().compareTo(deliveryPrice) == 0);
        assertTrue(order.getTotalPrice().compareTo(COST.add(deliveryPrice)) == 0);

        assertEquals(0, order.getOrderItems().size());
    }

    @Test
    public void getOrder() {
        final Long ORDER_ID = 1000L;
        Order order = createOrder(ORDER_ID, 3);
        order.setId(ORDER_ID);

        when(orderDao.get(ORDER_ID)).thenReturn(Optional.of(order));

        Order newOrder = orderService.getOrder(ORDER_ID).get();

        verify(orderDao).get(eq(ORDER_ID));

        assertEquals(order, newOrder);
        checkTotalCost(newOrder);
    }

    @Test
    public void getNonexistentOrder() {
        final Long ORDER_ID = 1000L;

        when(orderDao.get(ORDER_ID)).thenReturn(Optional.empty());

        Optional<Order> orderOptional = orderService.getOrder(ORDER_ID);

        verify(orderDao).get(eq(ORDER_ID));

        assertFalse(orderOptional.isPresent());
    }

    @Test
    public void countOrders() {
        final int ORDER_COUNT = 1000;
        when(orderDao.orderCount()).thenReturn(ORDER_COUNT);

        int count = orderService.orderCount();

        verify(orderDao).orderCount();

        assertEquals(ORDER_COUNT, count);
    }

    @Test
    public void findAll() {
        final int OFFSET = 0;
        final int LIMIT = 10;
        final int COUNT = 5;

        List<Order> orderList = new ArrayList<>();

        for (int i = 1; i <= COUNT; i++) {
            orderList.add(createOrder((long) i, i));
        }

        when(orderDao.findAll(OFFSET, LIMIT)).thenReturn(orderList);

        List<Order> findAllOrderList = orderService.findAll(OFFSET, LIMIT);

        verify(orderDao).findAll(OFFSET, LIMIT);

        assertEquals(findAllOrderList, orderList);
        findAllOrderList.forEach(this::checkTotalCost);
    }

    @Test
    public void updateOrderStatus() throws OutOfStockException, OrderNotFoundException {
        final Long ORDER_ID = 1000L;
        final OrderStatus ORDER_STATUS = OrderStatus.DELIVERED;
        Order order = createOrder(ORDER_ID, 3);
        order.setId(ORDER_ID);

        when(orderDao.get(ORDER_ID)).thenReturn(Optional.of(order));

        orderService.updateOrderStatus(ORDER_ID, ORDER_STATUS);

        verify(orderDao).get(eq(ORDER_ID));
        verify(orderDao).save(order);
    }

    @Test(expected = OrderNotFoundException.class)
    public void updateNonexistentOrderStatus() throws OutOfStockException, OrderNotFoundException {
        final Long ORDER_ID = 1000L;
        final OrderStatus ORDER_STATUS = OrderStatus.DELIVERED;
        Order order = createOrder(ORDER_ID, 3);
        order.setId(ORDER_ID);

        when(orderDao.get(ORDER_ID)).thenReturn(Optional.empty());
        try {
            orderService.updateOrderStatus(ORDER_ID, ORDER_STATUS);
        } finally {
            verify(orderDao).get(eq(ORDER_ID));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateOrderStatusSetNew() throws OutOfStockException, OrderNotFoundException {
        final Long ORDER_ID = 1000L;
        final OrderStatus ORDER_STATUS = OrderStatus.NEW;
        Order order = createOrder(ORDER_ID, 3);
        order.setId(ORDER_ID);

        when(orderDao.get(ORDER_ID)).thenReturn(Optional.of(order));
        try {
            orderService.updateOrderStatus(ORDER_ID, ORDER_STATUS);
        } finally {
            verify(orderDao).get(eq(ORDER_ID));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateOrderStatusWithNotNewStatus() throws OutOfStockException, OrderNotFoundException {
        final Long ORDER_ID = 1000L;
        final OrderStatus ORDER_STATUS = OrderStatus.REJECTED;
        Order order = createOrder(ORDER_ID, 3);
        order.setId(ORDER_ID);
        order.setStatus(OrderStatus.DELIVERED);

        when(orderDao.get(ORDER_ID)).thenReturn(Optional.of(order));
        try {
            orderService.updateOrderStatus(ORDER_ID, ORDER_STATUS);
        } finally {
            verify(orderDao).get(eq(ORDER_ID));
        }
    }

    private void checkTotalCost(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            assertTrue(orderItem.getTotal().compareTo(
                    orderItem.getPhone().getPrice().multiply(new BigDecimal(orderItem.getQuantity()))) == 0);
        }
    }
}
