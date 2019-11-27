package com.es.core.service.phone;

import com.es.core.cart.Cart;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class PhoneServiceTest {
    @Mock
    private PhoneDao phoneDao;

    @InjectMocks
    private PhoneService phoneService;

    private final Long PHONE_ID = 1000L;
    private final Long QUANTITY = 10L;

    @Before
    public void setupMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPhonesFromCart(){
        Phone phone = new Phone();
        phone.setId(PHONE_ID);
        Mockito.when(phoneDao.get(PHONE_ID)).thenReturn(Optional.of(phone));
        Cart cart = new Cart();
        cart.addPhone(PHONE_ID, QUANTITY);
        List<Phone> phones = phoneService.getPhonesFromCart(cart);

        Assert.assertTrue(phones.size() == 1);
        phone = phones.get(0);
        Assert.assertTrue(phone.getId().equals(PHONE_ID));
    }
}
