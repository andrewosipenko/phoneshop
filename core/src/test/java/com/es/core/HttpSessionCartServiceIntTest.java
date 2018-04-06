package com.es.core;

import com.es.core.cart.Cart;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.dao.PhoneDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:context/testContext-core.xml")
public class HttpSessionCartServiceIntTest {
    private final long EXISTING_PHONE_WITH_PRICE_ID = 1003L;
    private final long NOT_EXISTING_PHONE_ID = 1L;
    private final BigDecimal PHONE_PRICE = new BigDecimal(249);
    private final long QUANTITY = 2;
    @Autowired
    HttpSessionCartService cartService;
    @Autowired
    PhoneDao phoneDao;
    @Autowired
    MockHttpSession mockHttpSession;

    @Test()
    public void testGetCart(){
        Cart cart = cartService.getCart();
        Assert.assertNotNull(cart);
    }

    @Test
    public void testAddPhone(){
        cartService.addPhone(EXISTING_PHONE_WITH_PRICE_ID, QUANTITY);
        BigDecimal expectedSubtotalValue = PHONE_PRICE.multiply(new BigDecimal(QUANTITY));
        BigDecimal subtotal = cartService.getCart().getSubtotal();
        Assert.assertTrue(expectedSubtotalValue.compareTo(subtotal) == 0);

        Long expectedQuantityValue = QUANTITY;
        Long quantity = cartService.getCart().getItemsAmount();
        Assert.assertTrue(expectedQuantityValue.equals(quantity));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddPhoneNotExistingId(){
        cartService.addPhone(NOT_EXISTING_PHONE_ID, QUANTITY);
    }
}