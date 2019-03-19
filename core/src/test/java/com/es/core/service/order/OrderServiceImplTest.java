package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.dao.orderItem.OrderItemDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.color.Color;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.customer.CustomerInfo;
import com.es.core.model.phone.Phone;
import com.es.core.util.PhoneCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    private static final String MODEL = "3";
    private static final String BRAND = "ARCHOS";
    private static final String NAME = "3";
    private static final String LAST_NAME = "3";
    private static final String ADDRESS = "3";
    private static final String PHONE = "3";
    private static final String ADDITIONAL_INFO = "3";
    private static final Long ID = 1000L;
    private static final Long QUANTITY = 1L;
    private static final Set<Color> COLORS = new HashSet<>();
    private static final String SECURE_ID = "secure_id";

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderItemDao orderItemDao;

    private Phone phone;
    private Order order;

    @Before
    public void setUp() {
        phone = PhoneCreator.createPhone(ID, BRAND, MODEL, COLORS);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(phone, QUANTITY));
        order = new Order();
        order.setOrderItems(orderItems);
        order.setId(3L);
    }

    @Test
    public void shouldReturnOrderWithInfoFromCart() {
        CustomerInfo customerInfo = new CustomerInfo();

        orderService.createOrder(new Cart(), customerInfo, new BigDecimal(4));

        verify(orderDao, times(1)).save(any());
        verify(orderItemDao, times(1)).save(any(), any());
    }

    @Test
    public void shouldReturnOrderIdBySecureId() {
        Order actualOrder = orderDao.findOrder(SECURE_ID);

        verify(orderDao, times(1)).findOrder(any());
    }
}