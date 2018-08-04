package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.order.dao.OrderDao;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.model.OrderItem;
import com.es.phoneshop.core.order.model.OrderStatus;
import com.es.phoneshop.core.order.throwable.EmptyCartPlacingOrderException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.testutils.PhoneComparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderServiceIntTest {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDao orderDao; // needed for 1 test
    @Resource
    private CartService cartService;
    private static Comparator<Phone> phoneComparator = new PhoneComparator();

    private static final Long[] EXISTING_PHONE_IDS = {1001L, 1002L, 1003L, 1004L};
    private static final Long[] PHONE_ACCEPTABLE_QUANTITITES = {5L, 9L, 2L, 4L};
    private static final Long PHONE_WITH_NO_STOCK = 1006L;
    private static final String FIRST_NAME = "Egor";
    private static final String LAST_NAME = "Poryadin";
    private static final String DELIVERY_ADDRESS = "Glebky, 82-37";
    private static final String CONTACT_PHONE_NUMBER = "+375291458433";
    private static final String ADDITIONAL_INFO = "Some Info";

    @Test
    public void testCreateOrder() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITITES[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITITES[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITITES[2]);
        Order order = orderService.createOrder(cartService.getCart());
        OrderItem orderItem1 = order.getOrderItems().get(0);
        OrderItem orderItem2 = order.getOrderItems().get(1);
        OrderItem orderItem3 = order.getOrderItems().get(2);

        assertEquals(0, order.getDeliveryPrice().compareTo(cartService.getCart().getDeliveryPrice()));
        assertEquals(0, order.getSubtotal().compareTo(cartService.getCart().getSubtotal()));
        assertEquals(0, order.getTotalPrice().compareTo(cartService.getCart().getSubtotal().add(cartService.getCart().getDeliveryPrice())));
        assertSame(cartService.getCart().getCartItems().get(0).getPhone(), orderItem1.getPhone());
        assertSame(cartService.getCart().getCartItems().get(1).getPhone(), orderItem2.getPhone());
        assertSame(cartService.getCart().getCartItems().get(2).getPhone(), orderItem3.getPhone());
        assertEquals(PHONE_ACCEPTABLE_QUANTITITES[0], orderItem1.getQuantity());
        assertEquals(PHONE_ACCEPTABLE_QUANTITITES[1], orderItem2.getQuantity());
        assertEquals(PHONE_ACCEPTABLE_QUANTITITES[2], orderItem3.getQuantity());
        assertSame(order, orderItem1.getOrder());
        assertSame(order, orderItem2.getOrder());
        assertSame(order, orderItem3.getOrder());
    }

    @Test
    public void testPlaceOrder() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITITES[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITITES[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITITES[2]);
        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(CONTACT_PHONE_NUMBER);
        orderService.placeOrder(order);
    }

    @Test(expected = NoStockFoundException.class)
    public void testPlaceOrderNoStock() {
        Phone phone = new Phone();
        phone.setId(PHONE_WITH_NO_STOCK);
        phone.setPrice(BigDecimal.valueOf(200));
        cartService.getCart().getCartItems().add(new CartItem(phone, 5L));
        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(CONTACT_PHONE_NUMBER);
        order.setAdditionalInformation(ADDITIONAL_INFO);
        orderService.placeOrder(order);
    }

    @Test(expected = EmptyCartPlacingOrderException.class)
    public void testPlaceOrderEmptyCart() {
        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(CONTACT_PHONE_NUMBER);
        orderService.placeOrder(order);
    }

    @Test
    public void testGetOrder() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITITES[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITITES[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITITES[2]);
        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(CONTACT_PHONE_NUMBER);
        orderService.placeOrder(order);
        Optional<Order> order2Optional = orderService.getOrder(order.getId());
        assertTrue(order2Optional.isPresent());
        Order order2 = order2Optional.get();

        assertEquals(order.getAdditionalInformation(), order2.getAdditionalInformation());
        assertEquals(order.getFirstName(), order2.getFirstName());
        assertEquals(order.getLastName(), order2.getLastName());
        assertEquals(order.getDeliveryAddress(), order2.getDeliveryAddress());
        assertEquals(order.getContactPhoneNo(), order2.getContactPhoneNo());
        assertEquals(0, order.getDeliveryPrice().compareTo(order2.getDeliveryPrice()));
        assertEquals(0, order.getSubtotal().compareTo(order2.getSubtotal()));
        assertEquals(0, order.getTotalPrice().compareTo(order2.getTotalPrice()));
        assertSame(order.getStatus(), order2.getStatus());
        assertEquals(order.getId(), order2.getId());

        List<OrderItem> orderItems1 = order.getOrderItems().stream()
                .sorted((item1, item2) -> (int) (item1.getPhone().getId() - item2.getPhone().getId()))
                .collect(Collectors.toList());
        List<OrderItem> orderItems2 = order2.getOrderItems().stream()
                .sorted((item1, item2) -> (int) (item1.getPhone().getId() - item2.getPhone().getId()))
                .collect(Collectors.toList());
        for (int i = 0; i < orderItems1.size(); i++) {
            assertEquals(orderItems1.get(i).getQuantity(), orderItems2.get(i).getQuantity());
            assertEquals(0, phoneComparator.compare(orderItems1.get(i).getPhone(), orderItems2.get(i).getPhone()));
        }
    }

    @Test
    public void testGetOrderUnexisting() {
        Optional<Order> orderOptional = orderService.getOrder("asasdasd");
        assertFalse(orderOptional.isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlaceOrderDuplicateId() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITITES[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITITES[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITITES[2]);
        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(CONTACT_PHONE_NUMBER);
        orderService.placeOrder(order);
        orderDao.save(order);
    }

    @Test
    public void testGetAllOrders() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITITES[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITITES[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITITES[2]);
        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(CONTACT_PHONE_NUMBER);
        orderService.placeOrder(order);
        List<Order> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());
        Order actual = orders.get(0);
        assertEquals(order.getFirstName(), actual.getFirstName());
        assertEquals(order.getDeliveryAddress(), actual.getDeliveryAddress());
        assertEquals(order.getContactPhoneNo(), actual.getContactPhoneNo());
        assertSame(order.getStatus(), actual.getStatus());
    }

    @Test
    public void testSetOrderStatus() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITITES[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITITES[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITITES[2]);
        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(CONTACT_PHONE_NUMBER);
        orderService.placeOrder(order);
        orderService.setOrderStatus(order.getId(), OrderStatus.REJECTED);
        Optional<Order> orderOptional = orderService.getOrder(order.getId());
        assertTrue(orderOptional.isPresent());
        assertSame(OrderStatus.REJECTED, orderOptional.get().getStatus());
    }
}
