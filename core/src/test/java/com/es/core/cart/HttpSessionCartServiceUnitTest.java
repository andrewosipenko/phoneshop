package com.es.core.cart;

import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HttpSessionCartServiceUnitTest {

    @Test
    public void getCartSubTotalTest(){
        HttpSessionCartService cartService = new HttpSessionCartService();
        ReflectionTestUtils.invokeMethod(cartService, "init");

        float[] prices = {200, 350, 400.5f, 150};
        long[] quantities = {2, 2, 1, 3};
        List<Phone> phones = new ArrayList<>();

        Phone phone;
        for(float price : prices){
            phone = mock(Phone.class);
            when(phone.getPrice()).thenReturn(BigDecimal.valueOf(price));
            phones.add(phone);
        }
        Map<Long, CartEntry> cartEntries = cartService.getCart().getProducts();
        for(int i = 0; i < prices.length; i++){
            cartEntries.put((long) i, new CartEntry(phones.get(i), quantities[i]));
        }

        float sum = 0;
        for(int i = 0; i < prices.length; i++){
            sum += prices[i] * quantities[i];
        }
        BigDecimal expected = BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal actual = cartService.getCartSubTotal();

        assertEquals(expected, actual);
    }

    @Test
    public void getCartSubTotalTestOnEmptyCart(){
        HttpSessionCartService cartService = new HttpSessionCartService();
        ReflectionTestUtils.invokeMethod(cartService, "init");

        BigDecimal expected = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal actual = cartService.getCartSubTotal();

        assertEquals(expected, actual);
    }
}
