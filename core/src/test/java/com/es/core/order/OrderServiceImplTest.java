package com.es.core.order;

import com.es.core.AbstractTest;
import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.dao.order.OrderDao;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
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
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest extends AbstractTest {

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    @Mock
    private OrderDao orderDao;

    private BigDecimal deliveryPrice = new BigDecimal(5);

    private List<Phone> phones;

    @Before
    public void initDeliveryPrice() {
        ReflectionTestUtils.setField(orderService, "deliveryPrice", deliveryPrice);
    }

    @Before
    public void initPhoneList() {
        phones = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            phones.add(createPhone("test" + Integer.toString(i), "test" + Integer.toString(i), null, i));
        }
    }

    @After
    public void resetPhoneMockDao() {
        try {
            verifyNoMoreInteractions(orderDao);
        } finally {
            reset(orderDao);
        }
    }

    @Test
    public void placeOrderCheck() throws OutOfStockException {
        Order order = new Order();
        orderService.placeOrder(order);
        verify(orderDao).save(eq(order));
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
        items.add(new CartItem(phones.get(0), 1L));
        items.add(new CartItem(phones.get(1), 2L));
        items.add(new CartItem(phones.get(2), 3L));
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
}
