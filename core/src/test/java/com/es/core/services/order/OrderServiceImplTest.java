package com.es.core.services.order;

import com.es.core.dao.OrderDao;
import com.es.core.dao.StockDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.services.phone.PhoneService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    private static final long FIRST_PHONE_ID = 1L;
    private static final long SECOND_PHONE_ID = 2L;
    private static final int FIRST_ITEM_QUANTITY = 1;
    private static final int SECOND_ITEM_QUANTITY = 2;
    private Phone firstPhone = new Phone();
    private Phone secondPhone = new Phone();
    private List<CartItem> cartItems = new ArrayList<>();
    private PhoneService phoneService = mock(PhoneService.class);
    private OrderPriceService orderPriceService = mock(OrderPriceService.class);
    private OrderDao orderDao = mock(OrderDao.class);
    private StockDao stockDao = mock(StockDao.class);
    private OrderServiceImpl orderService = new OrderServiceImpl(phoneService, orderPriceService, orderDao, stockDao);
    private Cart cart = new Cart();

    @Before
    public void setUp() {
        firstPhone.setId(FIRST_PHONE_ID);
        secondPhone.setId(SECOND_PHONE_ID);
        cartItems.clear();
        cartItems.add(new CartItem(FIRST_PHONE_ID, FIRST_ITEM_QUANTITY));
        cartItems.add(new CartItem(SECOND_PHONE_ID, SECOND_ITEM_QUANTITY));
        cart.setCartItems(cartItems);
        when(phoneService.get(FIRST_PHONE_ID)).thenReturn(Optional.of(firstPhone));
        when(phoneService.get(SECOND_PHONE_ID)).thenReturn(Optional.of(secondPhone));
        //stockDao when()
    }

    @Test
    public void shouldConvertCartItemsToOrderItems() {
        Order order = new Order();
        List<OrderItem> expectedItems = new ArrayList<>();
        expectedItems.add(createOrderItem(order, firstPhone, FIRST_ITEM_QUANTITY));
        expectedItems.add(createOrderItem(order, secondPhone, SECOND_ITEM_QUANTITY));
        Phone la = phoneService.get(FIRST_PHONE_ID).get();

        List<OrderItem> actualItems = orderService.convertCartItemsToOrderItems(cart.getCartItems(), order);

        assertEquals(expectedItems, actualItems);
    }

    private OrderItem createOrderItem(Order order, Phone phone, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setPhone(phone);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    @Test
    public void shouldReturnCorrectOrder() {

    }
}