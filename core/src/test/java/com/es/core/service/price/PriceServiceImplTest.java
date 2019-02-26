package com.es.core.service.price;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.util.PhoneCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PriceServiceImplTest {
    private final static String MODEL = "1";
    private final static String BRAND = "2";
    private final static Long ID = 4L;
    private final static Long QUANTITY = 3L;
    private final static BigDecimal PRICE = new BigDecimal(10);
    private final static Set<Color> COLORS = new HashSet<>();

    private final static PriceService priceServer = new PriceServiceImpl();

    private Cart cart;

    @Before
    public void setUp() {
        Phone phone = PhoneCreator.createPhone(ID, BRAND, MODEL, COLORS);
        phone.setPrice(PRICE);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(QUANTITY, phone));

        cart = new Cart();
        cart.setCartItems(cartItems);
    }

    @Test
    public void shouldRecalculatePrice() {
        BigDecimal expectedPrice = new BigDecimal(30);

        priceServer.recalculatePrice(cart);
        BigDecimal actualPrice = cart.getTotalPrice();

        Assert.assertEquals(expectedPrice, actualPrice);
    }
}