package com.es.core.cart.cost;

import com.es.core.cart.Cart;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static util.PhoneUtils.createPhone;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCostServiceTest {
    @InjectMocks
    private CostServiceImpl httpSessionCostService;

    @Mock
    private HttpSessionCartService httpSessionCartServiceMock;

    private List<Phone> phoneList;
    private static final int PHONE_COUNT = 5;

    @Before
    public void initPhoneList() {
        phoneList = new ArrayList<>();
        for (int i = 1; i <= PHONE_COUNT; i++) {
            phoneList.add(createPhone(i));
        }
    }

    @Test
    public void checkCostWhenNoItemsInCart() {
        when(httpSessionCartServiceMock.getCart()).thenReturn(new Cart());
        assertEquals(BigDecimal.ZERO, httpSessionCostService.getCost(httpSessionCartServiceMock.getCart()));
    }

    @Test
    public void checkCostWhenItemsInCart() {
        final BigDecimal COST = BigDecimal.valueOf(1500D);
        Map<Phone, Long> items = phoneList.stream().collect(Collectors.toMap(phone -> phone, phone -> 1L));
        Cart cart = new Cart();
        cart.setItems(items);
        when(httpSessionCartServiceMock.getCart()).thenReturn(cart);
        assertEquals(COST, httpSessionCostService.getCost(httpSessionCartServiceMock.getCart()));
    }
}