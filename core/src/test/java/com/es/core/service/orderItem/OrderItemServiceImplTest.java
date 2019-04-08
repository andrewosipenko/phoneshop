package com.es.core.service.orderItem;

import com.es.core.dao.orderItem.OrderItemDao;
import com.es.core.model.color.Color;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.stock.StockService;
import com.es.core.util.PhoneCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemServiceImplTest {
    private static final long ID = 1003L;
    private static final String ARCHOS = "ARCHOS";
    private static final String MODEL = "3";
    private static final HashSet<Color> COLORS = new HashSet<>();
    private static final long QUANTITY = 4L;
    private static final long ORDER_ID = 3L;

    @InjectMocks
    private OrderItemService orderItemService = new OrderItemServiceImpl();

    @Mock
    private OrderItemDao orderItemDao;

    @Mock
    private StockService stockService;

    private Phone phone;
    private OrderItem orderItem;

    @Before
    public void setUp() {
        phone = PhoneCreator.createPhone(ID, ARCHOS, MODEL, COLORS);
        orderItem = new OrderItem(phone, QUANTITY);
    }

    @Test
    public void shouldSaveOrderItem() {
        orderItemService.save(Collections.singletonList(orderItem), ORDER_ID);

        verify(orderItemDao, times(1)).save(orderItem, ORDER_ID);
        verify(stockService, times(1)).addReserved(ID, QUANTITY);
    }
}