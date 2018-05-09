package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.order.dao.OrderDao;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.model.OrderItem;
import com.es.phoneshop.core.order.model.OrderStatus;
import com.es.phoneshop.core.order.throwable.EmptyCartPlacingOrderException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.model.Stock;
import com.es.phoneshop.core.stock.service.StockService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderServiceTest {
    @Mock
    private StockService stockService;
    @Mock
    private CartService cartService;
    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    private static Phone phone1;
    private static Phone phone2;
    private static Phone phone3;
    private static Stock stock1;
    private static Stock stock2;
    private static Stock stock3;

    @BeforeClass
    public static void initPhonesAndStocks() {
        phone1 = new Phone();
        phone2 = new Phone();
        phone3 = new Phone();
        phone1.setId(1001L);
        phone2.setId(1002L);
        phone3.setId(1003L);
        phone1.setPrice(BigDecimal.valueOf(200));
        phone2.setPrice(BigDecimal.valueOf(300));
        phone3.setPrice(BigDecimal.valueOf(150.50));

        stock1 = new Stock();
        stock2 = new Stock();
        stock3 = new Stock();
        stock1.setPhone(phone1);
        stock2.setPhone(phone2);
        stock3.setPhone(phone3);
        stock1.setStock(5L);
        stock2.setStock(7L);
        stock3.setStock(4L);
        stock1.setReserved(0L);
        stock2.setReserved(1L);
        stock3.setReserved(2L);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrder() {
        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(Stream.of(
                new CartItem(phone1, 3L),
                new CartItem(phone2, 4L),
                new CartItem(phone3, 1L)
        ).collect(Collectors.toList()));
        when(cart.getDeliveryPrice()).thenReturn(BigDecimal.valueOf(5));
        when(cart.getPhonesTotal()).thenReturn(8L);
        BigDecimal expectedSubtotal = (BigDecimal.valueOf(3).multiply(phone1.getPrice()))
                .add(BigDecimal.valueOf(4).multiply(phone2.getPrice()))
                .add(BigDecimal.valueOf(1).multiply(phone3.getPrice()));
        when(cart.getSubtotal()).thenReturn(expectedSubtotal);

        Order order = orderService.createOrder(cart);
        OrderItem orderItem1 = order.getOrderItems().get(0);
        OrderItem orderItem2 = order.getOrderItems().get(1);
        OrderItem orderItem3 = order.getOrderItems().get(2);

        assertEquals(0, order.getDeliveryPrice().compareTo(BigDecimal.valueOf(5)));
        assertEquals(0, order.getSubtotal().compareTo(expectedSubtotal));
        assertEquals(0, order.getTotalPrice().compareTo(expectedSubtotal.add(BigDecimal.valueOf(5))));
        assertSame(phone1, orderItem1.getPhone());
        assertSame(phone2, orderItem2.getPhone());
        assertSame(phone3, orderItem3.getPhone());
        assertEquals((Long) 3L, orderItem1.getQuantity());
        assertEquals((Long) 4L, orderItem2.getQuantity());
        assertEquals((Long) 1L, orderItem3.getQuantity());
        assertSame(order, orderItem1.getOrder());
        assertSame(order, orderItem2.getOrder());
        assertSame(order, orderItem3.getOrder());
    }

    @Test
    public void testGetOrder() {
        //just for code coverage to be happy
        orderService.getOrder("fafqwf245");
    }

    @Test
    public void testPlaceOrder() {
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        when(stockService.getStock(phone2)).thenReturn(Optional.of(stock2));
        when(stockService.getStock(phone3)).thenReturn(Optional.of(stock3));
        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(Stream.of(
                new CartItem(phone1, 3L),
                new CartItem(phone2, 4L),
                new CartItem(phone3, 1L)
        ).collect(Collectors.toList()));
        when(cart.getDeliveryPrice()).thenReturn(BigDecimal.valueOf(5));
        when(cart.getPhonesTotal()).thenReturn(8L);
        BigDecimal expectedSubtotal = (BigDecimal.valueOf(3).multiply(phone1.getPrice()))
                .add(BigDecimal.valueOf(4).multiply(phone2.getPrice()))
                .add(BigDecimal.valueOf(1).multiply(phone3.getPrice()));
        when(cart.getSubtotal()).thenReturn(expectedSubtotal);

        Order order = orderService.createOrder(cart);
        when(orderDao.isIdUnique(anyString())).thenReturn(true);
        orderService.placeOrder(order);
        verify(orderDao).save(order);
        assertSame(OrderStatus.NEW, order.getStatus());
    }

    @Test(expected = NoStockFoundException.class)
    public void testPlaceOrderNoStock() {
        when(stockService.getStock(phone1)).thenReturn(Optional.empty());
        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(Stream.of(
                new CartItem(phone1, 3L)
        ).collect(Collectors.toList()));
        when(cart.getDeliveryPrice()).thenReturn(BigDecimal.valueOf(5));
        when(cart.getPhonesTotal()).thenReturn(3L);
        BigDecimal expectedSubtotal = BigDecimal.valueOf(3).multiply(phone1.getPrice());
        when(cart.getSubtotal()).thenReturn(expectedSubtotal);

        Order order = orderService.createOrder(cart);
        orderService.placeOrder(order);
    }

    @Test(expected = EmptyCartPlacingOrderException.class)
    public void testPlaceOrderEmptyCart() {
        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(new ArrayList<>());
        when(cart.getDeliveryPrice()).thenReturn(BigDecimal.valueOf(5));
        when(cart.getPhonesTotal()).thenReturn(0L);
        when(cart.getSubtotal()).thenReturn(BigDecimal.ZERO);

        Order order = orderService.createOrder(cart);
        orderService.placeOrder(order);
    }
}
