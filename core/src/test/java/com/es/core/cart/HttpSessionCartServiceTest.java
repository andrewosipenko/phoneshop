package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testContext.xml")
public class HttpSessionCartServiceTest {
    @Resource
    private HttpSession httpSession;

    @Resource
    private CartService cartService;

    @Resource
    private PhoneService phoneService;

    private static final String ATTRIBUTE_CART = "cart";

    @Test
    public void checkGetCartWithoutCartInSession() {
        assertEquals(httpSession.getAttribute(ATTRIBUTE_CART), cartService.getCart());
    }

    @Test
    public void checkGetCartWithCartInSession() {
        Cart cart = new Cart();
        Phone phone = phoneService.get(1000L).get();
        cart.addItem(phone, 1L);
        httpSession.setAttribute(ATTRIBUTE_CART, cart);
        assertEquals(cart.getPrice(), cartService.getPrice());
    }

    @Test
    public void checkPrice() {
        Cart cart = mock(Cart.class);
        BigDecimal price = new BigDecimal("100");
        when(cart.getPrice()).thenReturn(price);
        httpSession.setAttribute(ATTRIBUTE_CART, cart);
        assertEquals(price, cartService.getPrice());
        verify(cart).getPrice();
    }

    @Test
    public void checkAddPhoneToCart() {
        Cart cart = mock(Cart.class);
        Phone phone = phoneService.get(1000L).get();
        httpSession.setAttribute(ATTRIBUTE_CART, cart);
        cartService.addPhone(phone.getId(), 1L);
        verify(cart).addItem(phone, 1L);
    }

    @Test
    public void checkRemovePhoneFromCart() {
        Cart cart = mock(Cart.class);
        Phone phone = phoneService.get(1000L).get();
        httpSession.setAttribute(ATTRIBUTE_CART, cart);
        cartService.remove(phone.getId());
        verify(cart).removeItem(phone);
    }

    @Test
    public void checkCountItemsInCart() {
        Cart cart = mock(Cart.class);
        long countItems = 10;
        when(cart.countItems()).thenReturn(countItems);
        httpSession.setAttribute(ATTRIBUTE_CART, cart);
        assertEquals(countItems, cartService.getCountItems());
        verify(cart).countItems();
    }

    @Test
    public void checkGetListOfItemsFromCart() {
        Cart cart = mock(Cart.class);
        Map<Phone, Long> phonesMap = new HashMap<Phone, Long>() {{
            put(phoneService.get(1000L).get(), 1L);
            put(phoneService.get(1001L).get(), 2L);
            put(phoneService.get(1002L).get(), 2L);
        }};

        List<Phone> phonesList = new LinkedList<>();
        phonesList.add(phoneService.get(1000L).get());
        phonesList.add(phoneService.get(1001L).get());
        phonesList.add(phoneService.get(1001L).get());
        phonesList.add(phoneService.get(1002L).get());
        phonesList.add(phoneService.get(1002L).get());

        when(cart.getItems()).thenReturn(phonesMap);
        httpSession.setAttribute(ATTRIBUTE_CART, cart);
        for(Phone phone : cartService.getAllItems()) {
            assertTrue(String.format("Phone with id %d not contain in list", phone.getId()), phonesList.contains(phone));
            phonesList.remove(phone);
        }
        assertTrue("Phone list contains more items then expected", phonesList.isEmpty());
        verify(cart).getItems();
    }
}