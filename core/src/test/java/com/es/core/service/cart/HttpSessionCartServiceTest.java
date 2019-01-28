package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.exceptions.phone.PhoneException;
import com.es.core.model.cart.Cart;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "/context/test-config.xml")
public class HttpSessionCartServiceTest {
    private final long EXISTING_PHONE_ID = 1003l;
    private final long NOT_EXISTING_PHONE_ID = -1l;
    private final long PHONE_QUANTITY = 1;
    private final long PHONE_NEW_QUANTITY = 3;
    private final BigDecimal PHONE_PRICE = new BigDecimal(249);
    private final BigDecimal PHONE_SUBTOTAL = PHONE_PRICE.multiply(BigDecimal.valueOf(PHONE_QUANTITY));
    private final BigDecimal NEW_PHONE_SUBTOTAL = PHONE_PRICE.multiply(BigDecimal.valueOf(PHONE_NEW_QUANTITY));
    private Phone phone;
    private Map<Long, Long> existingItems;
    private Map<Long, Long> updatingItems;

    @InjectMocks
    private HttpSessionCartService httpSessionCartService = new HttpSessionCartService();
    @Mock
    private PhoneDao phoneDao;
    @Mock
    private Cart cart;
    @Mock
    private PriceService priceService;


    @Before
    public void initMock(){
        MockitoAnnotations.initMocks(this);
        initObjects();
        initBehaviour();
    }

    private void initObjects(){
        phone = new Phone();
        phone.setId(EXISTING_PHONE_ID);
        phone.setPrice(PHONE_PRICE);
        existingItems = new HashMap<>();
        existingItems.put(EXISTING_PHONE_ID, PHONE_QUANTITY);
        updatingItems = new HashMap<>();
        updatingItems.put(EXISTING_PHONE_ID, PHONE_NEW_QUANTITY);
    }

    private void initBehaviour(){
        Mockito.when(phoneDao.get(EXISTING_PHONE_ID)).thenReturn(Optional.of(phone));
        Mockito.when(phoneDao.get(NOT_EXISTING_PHONE_ID)).thenReturn(Optional.empty());
        Mockito.when(phoneDao.contains(EXISTING_PHONE_ID)).thenReturn(true);
        Mockito.when(phoneDao.contains(NOT_EXISTING_PHONE_ID)).thenReturn(false);
        Mockito.when(cart.getCartItems()).thenReturn(existingItems);
    }

    @Test
    public void shouldGetCart(){
        Cart newCart = httpSessionCartService.getCart();
        Assert.assertNotNull(newCart);
    }

    @Test
    public void shouldAddPhone(){
        Mockito.when(cart.getSubtotal()).thenReturn(BigDecimal.ZERO);
        Mockito.when(cart.getCartAmount()).thenReturn(0l);

        httpSessionCartService.addPhone(EXISTING_PHONE_ID, PHONE_QUANTITY);

        Mockito.verify(cart).setSubtotal(PHONE_SUBTOTAL);
        Mockito.verify(cart).setCartAmount(PHONE_QUANTITY);
    }

    @Test(expected = PhoneException.class)
    public void shouldThrowPhoneExceptionAddPhone(){
        httpSessionCartService.addPhone(NOT_EXISTING_PHONE_ID, PHONE_QUANTITY);
    }

    @Test
    public void shouldUpdateItems(){

    }

    @Test(expected = PhoneException.class)
    public void shouldThrowPhoneExceptionUpdateItems(){

    }

    @Test
    public void shouldRemovePhone(){

    }

    @Test(expected = PhoneException.class)
    public void shouldThrowPhoneExceptionRemovePhone(){
        httpSessionCartService.remove(NOT_EXISTING_PHONE_ID);
    }
}
