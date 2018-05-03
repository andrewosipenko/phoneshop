package com.es.core.service.phone;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class PhoneServiceIntTest {
    @Resource
    private PhoneService phoneService;

    private final Long PHONE_ID = 1000L;

    @Test
    public void getPhonesFromOrder(){
        Phone phone = new Phone();
        phone.setId(PHONE_ID);
        OrderItem orderItem = new OrderItem();
        orderItem.setPhone(phone);
        Order order = new Order();
        order.setOrderItems(Arrays.asList(orderItem));

        List<Phone> phones = phoneService.getPhonesFromOrder(order);
        Assert.assertTrue(phones.size() == 1);
        phone = phones.get(0);
        Assert.assertTrue(phone.getId().equals(PHONE_ID));
    }
}
