package com.es.core;

import com.es.core.cart.HttpSessionCartService;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Arrays;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/context/testContext-core.xml")
public class HttpSessionCartServiceIntTest {
    @Resource
    private HttpSessionCartService cartService;
    @Resource
    private MockHttpSession mockHttpSession;

    private final Long PHONE_ID_WITH_AVAILABLE_STOCK = 1003L;
    private final Long PHONE_ID_WITH_UNAVAILABLE_STOCK = 1001L;
    private final Long AVAILABLE_QUANTITY = 9L;
    private final Long UNAVAILABLE_QUANTITY = 100L;

    private final Integer PHONE_AMOUNT_TO_ADD = 3;
    private final Long EXISTING_PHONE_ID_TO_START_WITH = 1000L;
    @Test
    public void removePhonesOutOfTheStock(){
        Phone phoneWithAvailableStock = new Phone();
        Phone phoneWithUnavailableStock = new Phone();
        phoneWithAvailableStock.setId(PHONE_ID_WITH_AVAILABLE_STOCK);
        phoneWithUnavailableStock.setId(PHONE_ID_WITH_UNAVAILABLE_STOCK);
        cartService.addPhone(PHONE_ID_WITH_AVAILABLE_STOCK, AVAILABLE_QUANTITY);
        cartService.addPhone(PHONE_ID_WITH_UNAVAILABLE_STOCK, UNAVAILABLE_QUANTITY);

        Assert.assertTrue(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_AVAILABLE_STOCK));
        Assert.assertTrue(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_UNAVAILABLE_STOCK));
        cartService.removePhonesOutOfTheStock(Arrays.asList(phoneWithAvailableStock, phoneWithUnavailableStock));
        Assert.assertFalse(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_UNAVAILABLE_STOCK));
        Assert.assertTrue(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_AVAILABLE_STOCK));
    }

    @Test
    public void clearCart(){
        for(int i = 0; i < PHONE_AMOUNT_TO_ADD; ++i){
            cartService.addPhone(EXISTING_PHONE_ID_TO_START_WITH + i, (long) i);
        }
        Assert.assertTrue(cartService.getCart().getItems().size() == PHONE_AMOUNT_TO_ADD);
        cartService.clearCart();
        Assert.assertTrue(cartService.getCart().getItems().size() == 0);
    }
}
