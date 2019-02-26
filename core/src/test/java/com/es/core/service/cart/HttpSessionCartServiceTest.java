package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.service.price.PriceService;
import com.es.core.util.PhoneCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    private final static String MODEL = "1";
    private final static String BRAND = "2";
    private final static Long ID = 4L;
    private final static Long QUANTITY = 1L;
    private final static Set<Color> COLORS = new HashSet<>();

    @InjectMocks
    private final static CartService cartService = new HttpSessionCartService();

    @Mock
    private PhoneDao phoneDao;

    @Mock
    private PriceService priceService;

    @Test
    public void shouldAddCartItem() {
        Phone phone = PhoneCreator.createPhone(ID, BRAND, MODEL, COLORS);
        Cart cart = new Cart();
        when(phoneDao.get(ID)).thenReturn(Optional.of(phone));

        cartService.addCartItem(cart, phone.getId(), QUANTITY);
        Long actualCartItemQuantity = cart.getCartItems().get(0).getQuantity();

        Assert.assertEquals(QUANTITY, actualCartItemQuantity);
    }
}