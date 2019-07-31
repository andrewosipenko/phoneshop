package com.es.core.cart;

import com.es.core.model.ProductDao;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpSessionCartServiceTest {

    private static final double DELTA = 0;

    ProductDao productDao;

    Cart cart;

    HttpSessionCartService cartService = new HttpSessionCartService();

    @Before
    public void init() {
        cart = new Cart();
        cart.setProducts(Map.of(1010L, 3L, 4525L, 1L,
                9821L, 2L, 1766L, 5L, 5619L, 98L));
        Phone phone_1010 = new Phone();
        Phone phone_4525 = new Phone();
        Phone phone_9821 = new Phone();
        Phone phone_1766 = new Phone();
        Phone phone_5619 = new Phone();
        phone_1010.setPrice(new BigDecimal(1231));
        phone_4525.setPrice(new BigDecimal(3441));
        phone_9821.setPrice(new BigDecimal(1234));
        phone_1766.setPrice(new BigDecimal(8978));
        phone_5619.setPrice(new BigDecimal(6104));
        productDao = mock(ProductDao.class);
        when(productDao.loadPhoneById(1010)).thenReturn(phone_1010);
        when(productDao.loadPhoneById(4525)).thenReturn(phone_4525);
        when(productDao.loadPhoneById(9821)).thenReturn(phone_9821);
        when(productDao.loadPhoneById(1766)).thenReturn(phone_1766);
        when(productDao.loadPhoneById(5619)).thenReturn(phone_5619);
        cartService.setCart(cart);
        cartService.setProductDao(productDao);
        cartService.updateTotals();
    }

    @Test
    public void testTotalCount() {
        long expectedTotalCount = 5;
        long actualTotalCount = cart.getTotalCount();
        Assert.assertEquals(expectedTotalCount, actualTotalCount);
    }

    @Test
    public void testTotalPrice() {
        double expectedTotalPrice = 652684;
        double actualTotalPrice = cart.getTotalPrice();
        Assert.assertEquals(expectedTotalPrice, actualTotalPrice, DELTA);
    }
}
