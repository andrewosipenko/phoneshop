package com.es.core.cart;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class HttpSessionCartServiceIntTest {
    @Resource
    private HttpSessionCartService cartService;
    @Resource
    private PhoneDao phoneDao;
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
        BigDecimal priceOfPhoneWithAvailableStock = phoneDao.get(PHONE_ID_WITH_AVAILABLE_STOCK).get().getPrice();
        BigDecimal priceOfPhoneWithUnavailableStock = phoneDao.get(PHONE_ID_WITH_UNAVAILABLE_STOCK).get().getPrice();
        BigDecimal subtotalOfAvailablePhone = priceOfPhoneWithAvailableStock.multiply(BigDecimal.valueOf(AVAILABLE_QUANTITY));
        BigDecimal subtotalOfUnavailablePhone = priceOfPhoneWithUnavailableStock.multiply(BigDecimal.valueOf(UNAVAILABLE_QUANTITY));
        BigDecimal subtotal = subtotalOfAvailablePhone.add(subtotalOfUnavailablePhone);

        cartService.addPhone(PHONE_ID_WITH_AVAILABLE_STOCK, AVAILABLE_QUANTITY);
        cartService.addPhone(PHONE_ID_WITH_UNAVAILABLE_STOCK, UNAVAILABLE_QUANTITY);

        Assert.assertTrue(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_AVAILABLE_STOCK));
        Assert.assertTrue(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_UNAVAILABLE_STOCK));
        Assert.assertTrue(cartService.getCart().getSubtotal().compareTo(subtotal) == 0);

        cartService.removePhonesOutOfTheStock();

        Assert.assertFalse(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_UNAVAILABLE_STOCK));
        Assert.assertTrue(cartService.getCart().getItems().containsKey(PHONE_ID_WITH_AVAILABLE_STOCK));
        Assert.assertTrue(cartService.getCart().getSubtotal().compareTo(subtotalOfAvailablePhone) == 0);
    }

    @Test
    public void clearCart(){
        for(int i = 0; i < PHONE_AMOUNT_TO_ADD; ++i){
            cartService.addPhone(EXISTING_PHONE_ID_TO_START_WITH + i, (long) i);
        }
        Assert.assertTrue(cartService.getCart().getItems().size() == PHONE_AMOUNT_TO_ADD);
        cartService.clearCart();
        Assert.assertTrue(cartService.getCart().getItems().size() == 0);
        Assert.assertTrue(cartService.getCart().getSubtotal().compareTo(BigDecimal.ZERO) == 0);
        Assert.assertTrue(cartService.getCart().getItemsAmount().equals(0L));
    }
}
