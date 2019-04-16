package com.es.core.service.phone;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context/test-config.xml")
public class PhoneServiceImplTest {
    private final Long PHONE_ID = 1001L;
    private final Long QUANTITY = 1L;
    private final Long NEW_PHONE_ID = 10000L;
    private final String NEW_PHONE_BRAND = "New Brand";
    private final String NEW_PHONE_MODEL = "New Model";

    @Resource
    private PhoneDao phoneDao;
    @Resource
    private PhoneService phoneService;

    @Test
    public void shouldGetPhoneById(){
        Phone phone = phoneService.getPhone(PHONE_ID).get();

        Assert.assertEquals(phone.getId(), PHONE_ID);
    }

    @Test
    public void shouldSavePhone(){
        long oldMaxId = phoneDao.getMaxPhoneId();
        Phone phone = new Phone();
        phone.setId(NEW_PHONE_ID);
        phone.setBrand(NEW_PHONE_BRAND);
        phone.setModel(NEW_PHONE_MODEL);

        phoneService.save(phone);
        long newMaxId = phoneDao.getMaxPhoneId();

        Assert.assertEquals(oldMaxId + 1, newMaxId);
    }

    @Test
    public void shouldDeletePhone(){
        Phone phone = new Phone();
        phone.setId(PHONE_ID);
        long oldCount = phoneDao.getCountPhones();

        phoneService.delete(phone);
        long newCount = phoneDao.getCountPhones();

        Assert.assertEquals(oldCount - 1, newCount);
    }

    @Test
    public void shouldGetListPhonesFromCart(){
        Phone phone = new Phone();
        phone.setId(PHONE_ID);

        Cart cart = new Cart();
        //cart.addPhone(PHONE_ID, QUANTITY);
        List<Phone> phones = phoneService.getPhoneListFromCart(cart);

        phone = phones.get(0);
        Assert.assertEquals(phones.size(), 1);
        Assert.assertEquals(phone.getId(), PHONE_ID);
    }
}
