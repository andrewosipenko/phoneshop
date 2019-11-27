package com.es.core.service.order.orderDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class OrderDaoServiceIntTest {
    @Resource
    private OrderDaoService orderDaoService;

    @Test
    public void generateOrderKey(){
        String orderKey = orderDaoService.generateOrderKey();
        Assert.assertTrue(orderKey != null);
        Assert.assertTrue(orderKey.length() == 32);
        Assert.assertFalse(orderKey.contains("-"));
    }
}
