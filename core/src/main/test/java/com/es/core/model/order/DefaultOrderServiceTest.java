package com.es.core.model.order;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.PhoneDao;
import com.es.core.order.OrderService;
import com.es.core.order.UserContactInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class DefaultOrderServiceTest {
    public static final long ACTUAL_PHONE_ID = 1000L;
    public static final long ACTUAL_TOTAL_QUANTITY = 1L;
    public static final BigDecimal ACTUAL_TOTAL_PRICE = new BigDecimal("254.0");
    public static final BigDecimal ACTUAL_SUBTOTAL = new BigDecimal("249.0");
    public static final String TEST = "test";
    public static final BigDecimal ACTUAL_DELIVERY_PRICE = new BigDecimal("5.0");
    public static final long EXPECTED_ORDER_ID = 4L;
    @Resource
    private OrderDao orderDao;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private OrderService orderService;

    private Cart cartExample;
    private Order actualOrder;
    private UserContactInfo contactInfo;

    @Before
    public void init(){
        cartExample = new Cart();
        cartExample.setTotalCost(ACTUAL_SUBTOTAL);

        CartItem cartItem = new CartItem();
        cartItem.setPhone(phoneDao.get(ACTUAL_PHONE_ID).get());
        cartItem.setQuantity(1);
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        cartExample.setCartItems(cartItemList);
        cartExample.setTotalQuantity(ACTUAL_TOTAL_QUANTITY);

        contactInfo = new UserContactInfo();
        contactInfo.setFirstName(TEST);
        contactInfo.setLastName(TEST);
        contactInfo.setDeliveryAddress(TEST);
        contactInfo.setContactPhoneNo(TEST);

        actualOrder = new Order();
        actualOrder.setId(4L);
        actualOrder.setTotalPrice(ACTUAL_TOTAL_PRICE);
        actualOrder.setSubtotal(ACTUAL_SUBTOTAL);
        actualOrder.setFirstName(TEST);
        actualOrder.setLastName(TEST);
        actualOrder.setDeliveryAddress(TEST);
        actualOrder.setContactPhoneNo(TEST);
        actualOrder.setStatus(OrderStatus.NEW);
        actualOrder.setDeliveryPrice(ACTUAL_DELIVERY_PRICE);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(actualOrder);
        orderItem.setPhoneId(ACTUAL_PHONE_ID);
        orderItem.setPhone(phoneDao.get(ACTUAL_PHONE_ID).get());
        orderItem.setQuantity(1L);
        orderItemList.add(orderItem);
        orderItem.setId(5L);
        actualOrder.setOrderItems(orderItemList);
    }

    @Test
    public void shouldPhoneDaoExist() {
        assertNotNull(orderService);
    }

    @Test
    public void shouldCreateOrderWhenCreateOrderMethod(){
        Order expectedOrder = orderService.createOrder(cartExample, contactInfo);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void shouldPlaceOrderWhenPlaceOrderMethod(){
        orderService.placeOrder(actualOrder);
        assertEquals(orderDao.getOrder(EXPECTED_ORDER_ID).get(), actualOrder);
    }

}
