package com.es.test.phoneshop.core.cart;

import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@Transactional
public class CartServiceIntTest {
    @Resource
    private CartService cartService;

    private static final Long EXISTING_PHONE_A_ID = 1001L;
    private static final Long PHONE_A_ACCEPTABLE_QUANTITY_1 = 5L;
    private static final Long PHONE_A_ACCEPTABLE_QUANTITY_2 = 3L;
    private static final Long EXISTING_PHONE_B_ID = 1004L;
    private static final Long PHONE_B_ACCEPTABLE_QUANTITY = 9L;
    private static final BigDecimal PHONES_AB_TOTAL_COST = BigDecimal.valueOf(6518);
    private static final Long PHONE_WITH_NO_STOCK = 1006L;
    private static final Long PHONE_NON_EXISTING = 999L;
    private static final Long PHONE_A_TOO_MUCH_QUANTITY = 30L;

    @Test
    public void testAddPhoneNormal() {
        cartService.addPhone(EXISTING_PHONE_A_ID, PHONE_A_ACCEPTABLE_QUANTITY_1);
        cartService.addPhone(EXISTING_PHONE_B_ID, PHONE_B_ACCEPTABLE_QUANTITY);
        cartService.addPhone(EXISTING_PHONE_A_ID, PHONE_A_ACCEPTABLE_QUANTITY_2);
        List<CartItem> items = cartService.getCartItems();
        assertEquals(items.size(), 2);
        assertEquals(items.get(0).getPhone().getId(), EXISTING_PHONE_A_ID);
        assertEquals(items.get(0).getQuantity(), (Long) (PHONE_A_ACCEPTABLE_QUANTITY_1 + PHONE_A_ACCEPTABLE_QUANTITY_2));
        assertEquals(items.get(1).getPhone().getId(), EXISTING_PHONE_B_ID);
        assertEquals(items.get(1).getQuantity(), PHONE_B_ACCEPTABLE_QUANTITY);
        CartStatus status = cartService.getCartStatus();
        assertEquals(status.getPhonesTotal(), (Long) (PHONE_A_ACCEPTABLE_QUANTITY_1 + PHONE_A_ACCEPTABLE_QUANTITY_2 + PHONE_B_ACCEPTABLE_QUANTITY));
        assertTrue(status.getCostTotal().compareTo(PHONES_AB_TOTAL_COST) == 0);
    }

    @Test
    public void testAddPhoneWithNoStock() {
        try {
            cartService.addPhone(PHONE_WITH_NO_STOCK, 5L);
            fail();
        } catch (NoStockFoundException ignored) {}
    }

    @Test
    public void testAddPhoneNotExisting() {
        try {
            cartService.addPhone(PHONE_NON_EXISTING, 5L);
            fail();
        } catch (NoSuchPhoneException ignored) {}
    }

    @Test
    public void testAddPhoneTooMuch() {
        try {
            cartService.addPhone(EXISTING_PHONE_A_ID, PHONE_A_TOO_MUCH_QUANTITY);
            fail();
        } catch (TooBigQuantityException ignored) {}
    }
}
