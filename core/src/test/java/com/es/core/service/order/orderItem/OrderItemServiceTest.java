package com.es.core.service.order.orderItem;

import com.es.core.cart.Cart;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.exception.NoSuchPhoneException;
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
public class OrderItemServiceTest {
    @Mock
    private PhoneDao mockPhoneDao;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private final Long EXISTING_PHONE_ID = 1000L;
    private Phone existingPhone;

    private final Long NOT_EXISTING_PHONE_ID = 10L;

    private final Long QUANTITY = 10L;


    @Before
    public void setupMocks(){
        MockitoAnnotations.initMocks(this);
        initObjects();
    }

    private void initObjects(){
        existingPhone = new Phone();
        existingPhone.setId(EXISTING_PHONE_ID);

    }

    @Test
    public void getOrderItemListWithExistingPhones(){
        Cart cart = new Cart();
        cart.addPhone(EXISTING_PHONE_ID, QUANTITY);
        Mockito.when(mockPhoneDao.get(EXISTING_PHONE_ID)).thenReturn(Optional.of(existingPhone));

        List<OrderItem> orderItemList = orderItemService.getOrderItemList(cart);

        Assert.assertTrue(orderItemList.size() == 1);

        OrderItem orderItem = orderItemList.get(0);
        Assert.assertTrue(orderItem.getQuantity().equals(QUANTITY));
        Assert.assertTrue(orderItem.getPhone().getId().equals(EXISTING_PHONE_ID));
    }

    @Test(expected = NoSuchPhoneException.class)
    public void getOrderItemListWithNotExistingPhone(){
        Cart cart = new Cart();
        cart.addPhone(NOT_EXISTING_PHONE_ID, QUANTITY);
        Mockito.when(mockPhoneDao.get(NOT_EXISTING_PHONE_ID)).thenReturn(Optional.empty());

        orderItemService.getOrderItemList(cart);
    }
}
