package com.es.core;

import com.es.core.cart.Cart;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.dao.phoneDao.PhoneDao;
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

import java.math.BigDecimal;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(value = "classpath:context/testContext-core.xml")
public class HttpSessionCartServiceTest {
    @Mock
    private PhoneDao mockPhoneDao;
    @Mock
    private Cart mockCart;
    @InjectMocks
    private HttpSessionCartService cartService;

    private final Long EXISTING_PHONE_ID = 1L;
    private final Long NOT_EXISTING_PHONE_ID = -1L;
    private Phone existingPhone;
    private final Long QUANTITY = 3L;
    private final Long QUANTITY_FOR_UPDATE = 4L;
    private final BigDecimal PHONE_PRICE = new BigDecimal(100);
    private final BigDecimal SUBTOTAL = PHONE_PRICE.multiply(BigDecimal.valueOf(QUANTITY));
    private final BigDecimal NEW_SUBTOTAL = PHONE_PRICE.multiply(BigDecimal.valueOf(QUANTITY_FOR_UPDATE));

    private Map<Long, Long> updateItemsMap;
    private Map<Long, Long> existingItems;

    public HttpSessionCartServiceTest() {
    }

    @Before
    public void setupMock(){
        MockitoAnnotations.initMocks(this);
        initObjects();
        setupStubs();
    }

    private void initObjects(){
        existingPhone = new Phone();
        existingPhone.setId(EXISTING_PHONE_ID);
        existingPhone.setPrice(PHONE_PRICE);

        updateItemsMap = new HashMap<>();
        updateItemsMap.put(EXISTING_PHONE_ID, QUANTITY_FOR_UPDATE);

        existingItems = new HashMap<>();
        existingItems.put(EXISTING_PHONE_ID, QUANTITY);
    }

    private void setupStubs(){
        Mockito.when(mockPhoneDao.get(EXISTING_PHONE_ID)).thenReturn(Optional.of(existingPhone));
        Mockito.when(mockPhoneDao.get(NOT_EXISTING_PHONE_ID)).thenReturn(Optional.empty());
        Mockito.when(mockPhoneDao.contains(EXISTING_PHONE_ID)).thenReturn(true);
        Mockito.when(mockPhoneDao.contains(NOT_EXISTING_PHONE_ID)).thenReturn(false);
        Mockito.when(mockCart.getItems()).thenReturn(existingItems);
    }

    @Test()
    public void testGetCart(){
        Cart cart = cartService.getCart();
        Assert.assertNotNull(cart);
    }

    @Test
    public void testAddExistingPhone(){
        Mockito.when(mockCart.getSubtotal()).thenReturn(BigDecimal.ZERO);
        Mockito.when(mockCart.getItemsAmount()).thenReturn(0L);

        cartService.addPhone(EXISTING_PHONE_ID, QUANTITY);
        Mockito.verify(mockCart).addPhone(EXISTING_PHONE_ID, QUANTITY);
        Mockito.verify(mockCart).setSubtotal(SUBTOTAL);
        Mockito.verify(mockCart).setItemsAmount(QUANTITY);
    }

    @Test (expected = NoSuchPhoneException.class)
    public void testNotExistingPhone(){
        cartService.addPhone(NOT_EXISTING_PHONE_ID, QUANTITY);
    }

    @Test
    public void removeExistingPhone(){
        Mockito.when(mockCart.getSubtotal()).thenReturn(SUBTOTAL);
        Mockito.when(mockCart.getItemsAmount()).thenReturn(QUANTITY);
        Mockito.when(mockCart.getItemQuantity(EXISTING_PHONE_ID)).thenReturn(QUANTITY);

        cartService.remove(EXISTING_PHONE_ID);
        Mockito.verify(mockCart).setItemsAmount(0L);
        Mockito.verify(mockCart).setSubtotal(BigDecimal.ZERO);
    }

    @Test(expected = NoSuchPhoneException.class)
    public void removeNotExistingPhone(){
        cartService.remove(NOT_EXISTING_PHONE_ID);
    }

    @Test
    public void updateWithExistingInCartPhone(){
        Mockito.when(mockCart.getItemQuantity(EXISTING_PHONE_ID)).thenReturn(QUANTITY);
        Mockito.when(mockCart.getSubtotal()).thenReturn(SUBTOTAL);
        Mockito.when(mockCart.getItemsAmount()).thenReturn(QUANTITY);

        cartService.update(updateItemsMap);
        Mockito.verify(mockCart).setSubtotal(NEW_SUBTOTAL);
        Mockito.verify(mockCart).setItemsAmount(QUANTITY_FOR_UPDATE);
    }

    @Test(expected = NoSuchPhoneException.class)
    public void updateWithNotExistingInCartPhone(){
        Mockito.when(mockCart.getItems()).thenReturn(new HashMap<>());
        Mockito.when(mockCart.getSubtotal()).thenReturn(BigDecimal.ZERO);
        Mockito.when(mockCart.getItemsAmount()).thenReturn(0L);
        Mockito.when(mockCart.getItemQuantity(EXISTING_PHONE_ID)).thenThrow(NoSuchPhoneException.class);

        cartService.update(updateItemsMap);
    }
}