package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.service.price.PriceService;
import com.es.core.util.PhoneCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    private final static String MODEL = "3";
    private final static String BRAND = "ARCHOS";
    private final static Long ID = 1003L;
    private final static Long QUANTITY = 1L;
    private final static Set<Color> COLORS = new HashSet<>();
    private final static Long EXPECTED_QUANTITY = 2L;

    @InjectMocks
    private final static CartService cartService = new HttpSessionCartService();

    @Mock
    private PhoneDao phoneDao;

    @Mock
    private PriceService priceService;

    private Phone phone;
    private CartItem cartItem;
    private Cart cart;

    @Before
    public void setUp() throws Exception {
        cart = new Cart();
        phone = PhoneCreator.createPhone(ID, BRAND, MODEL, COLORS);
        cartItem = new CartItem(QUANTITY, phone);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
    }

    @Test
    public void shouldAddCartItem() {
        when(phoneDao.get(ID)).thenReturn(Optional.of(phone));

        cartService.addCartItem(cart, phone.getId(), QUANTITY);
        Long actualCartItemQuantity = cart.getCartItems().get(0).getQuantity();

        assertEquals(EXPECTED_QUANTITY, actualCartItemQuantity);
    }

    @Test
    public void shouldUpdateQuantityToPhone() {
        cartService.update(cart, Collections.singletonMap(ID, EXPECTED_QUANTITY));
        Long actualQuantity = cart.getCartItems().get(0).getQuantity();

        assertEquals(EXPECTED_QUANTITY, actualQuantity);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingCartNotContainCurrentPhoneId() {
        cartService.update(cart, Collections.singletonMap(11L, 11L));
    }

    @Test
    public void shouldRemoveCartItemFromCart() {
        int expectedSize = 0;

        cartService.remove(cart, ID);
        int actualSize = cart.getCartItems().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void shouldReturnMapForUpdatingCart() {
        Map<Long, Long> expectedMap = new HashMap<>();
        expectedMap.put(phone.getId(), EXPECTED_QUANTITY);

        Map<Long, Long> actualMap = cartService
                .createMapForUpdating(new Long[] {EXPECTED_QUANTITY}, cart.getCartItems());

        assertEquals(expectedMap, actualMap);
    }
}