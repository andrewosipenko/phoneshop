package com.es.test.phoneshop.core.cart;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.service.CartServiceImpl;
import com.es.phoneshop.core.phone.model.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@Transactional
public class CartServiceTest {
    @Mock
    private Cart cart;
    @InjectMocks
    private CartService cartService = new CartServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCartStatus() {
        Phone phone1 = new Phone();
        phone1.setPrice(BigDecimal.valueOf(200.50));

        Phone phone2 = new Phone();
        phone2.setPrice(BigDecimal.valueOf(99));

        Phone phone3 = new Phone();
        phone3.setPrice(BigDecimal.valueOf(59));

        when(cart.getItems()).thenReturn(Stream.of(
                new CartItem(phone1, 4L),
                new CartItem(phone2, 3L),
                new CartItem(phone3, 5L)
        ).collect(Collectors.toList()));

        CartStatus cartStatus = cartService.getCartStatus();
        assertEquals(cartStatus.getPhonesTotal(), (Long) 12L);
        assertTrue(cartStatus.getCostTotal().compareTo(BigDecimal.valueOf(1394)) == 0);
    }

    @Test
    public void testGetCartStatusWhenEmpty() {
        when(cart.getItems()).thenReturn(new ArrayList<>());
        CartStatus cartStatus = cartService.getCartStatus();
        assertEquals(cartStatus.getPhonesTotal(), (Long) 0L);
        assertTrue(cartStatus.getCostTotal().compareTo(BigDecimal.ZERO) == 0);
    }
}
