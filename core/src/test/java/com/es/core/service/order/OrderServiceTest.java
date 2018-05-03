package com.es.core.service.order;

import com.es.core.cart.Cart;
import com.es.core.dao.orderDao.OrderDao;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.exception.OutOfStockException;
import com.es.core.service.order.orderItem.OrderItemServiceImpl;
import com.es.core.service.phone.PhoneService;
import com.es.core.service.stock.StockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class OrderServiceTest {
    @Mock
    private OrderItemServiceImpl orderItemService;
    @Mock
    private StockService stockService;
    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    private final Long PHONE_ID = 1L;
    private final Long QUANTITY = 3L;
    private final BigDecimal PHONE_PRICE = BigDecimal.TEN;
    private final BigDecimal DELIVERY_PRICE = BigDecimal.ONE;

    private Phone phone;
    private OrderItem orderItem;
    private Cart cart;
    @Before
    public void setupMock(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(orderService, "deliveryPrice", DELIVERY_PRICE, BigDecimal.class);
        initObjects();
    }

    private void initObjects(){
        phone = new Phone();
        phone.setId(PHONE_ID);
        phone.setPrice(PHONE_PRICE);

        orderItem = new OrderItem();
        orderItem.setQuantity(QUANTITY);
        orderItem.setPhone(phone);

        cart = new Cart();
    }

    @Test
    public void createOrder(){
        Mockito.when(orderItemService.getOrderItemList(cart)).thenReturn(Arrays.asList(orderItem));

        Order order = orderService.createOrder(cart);

        Assert.assertTrue(order.getOrderItems().size() == 1);
        BigDecimal subtotal = PHONE_PRICE.multiply(BigDecimal.valueOf(QUANTITY));
        Assert.assertTrue(order.getSubtotal().compareTo(subtotal) == 0);
        Assert.assertTrue(order.getTotalPrice().compareTo(subtotal.add(DELIVERY_PRICE)) == 0);
    }

    @Test
    public void placeOrder() throws OutOfStockException{
        Order order = new Order();
        orderService.placeOrder(order);
        Mockito.verify(stockService).updateStocks(order);
        Mockito.verify(orderDao).save(order);
        Assert.assertTrue(order.getStatus().equals(OrderStatus.NEW));
    }

    @Test
    public void updateOrder(){
        Order order = new Order();
        Mockito.when(orderItemService.getOrderItemList(cart)).thenReturn(Arrays.asList(orderItem));
        orderService.updateOrder(order, cart);
        BigDecimal subtotal = PHONE_PRICE.multiply(BigDecimal.valueOf(QUANTITY));
        Assert.assertTrue(order.getSubtotal().compareTo(subtotal) == 0);
        Assert.assertTrue(order.getTotalPrice().compareTo(subtotal.add(DELIVERY_PRICE)) == 0);
    }
}
