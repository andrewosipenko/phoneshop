package com.es.test.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CartServiceIntTest {
    @Resource
    private CartService cartService;

    private static final Long[] EXISTING_PHONE_IDS = {1001L, 1002L, 1003L, 1004L}; // ascending
    private static final Long[] PHONE_ACCEPTABLE_QUANTITIES_1 = {5L, 9L, 2L, 4L};
    private static final Long[] PHONE_ACCEPTABLE_QUANTITIES_2 = {3L, 4L, 3L, 4L};
    private static final BigDecimal P0Q1_P0Q2_P1Q1_TOTAL_COST = BigDecimal.valueOf(7103);
    private static final Long PHONE_WITH_NO_STOCK = 1006L;
    private static final Long PHONE_NON_EXISTING = 999L;
    private static final Long PHONE_A_TOO_MUCH_QUANTITY = 30L;

    @Test
    public void testAddPhone() {
        cartService.addPhone(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.addPhone(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.addPhone(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_2[0]);
        List<CartItem> items = cartService.getCartItems();
        assertEquals(2, items.size());
        assertEquals(EXISTING_PHONE_IDS[0], items.get(0).getPhone().getId());
        assertEquals((Long) (PHONE_ACCEPTABLE_QUANTITIES_1[0] + PHONE_ACCEPTABLE_QUANTITIES_2[0]), items.get(0).getQuantity());
        assertEquals(EXISTING_PHONE_IDS[1], items.get(1).getPhone().getId());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_1[1], items.get(1).getQuantity());
        CartStatus status = cartService.getCartStatus();
        assertEquals((Long) (PHONE_ACCEPTABLE_QUANTITIES_1[0] + PHONE_ACCEPTABLE_QUANTITIES_1[1] + PHONE_ACCEPTABLE_QUANTITIES_2[0]), status.getPhonesTotal());
        assertTrue(status.getCostTotal().compareTo(P0Q1_P0Q2_P1Q1_TOTAL_COST) == 0);
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
            cartService.addPhone(EXISTING_PHONE_IDS[0], PHONE_A_TOO_MUCH_QUANTITY);
            fail();
        } catch (TooBigQuantityException ignored) {}
    }

    @Test
    public void testUpdate() {
        cartService.addPhone(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.addPhone(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.addPhone(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITIES_1[2]);
        cartService.addPhone(EXISTING_PHONE_IDS[3], PHONE_ACCEPTABLE_QUANTITIES_1[3]);
        Long[][] updateArray = {
                {EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_2[0]},
                {EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_2[1]},
                {EXISTING_PHONE_IDS[3], PHONE_ACCEPTABLE_QUANTITIES_2[3]}
        };
        Map<Long, Long> updateMap = Stream.of(updateArray)
                .collect(Collectors.toMap(ar -> ar[0], ar -> ar[1]));
        cartService.update(updateMap);
        List<CartItem> cartItems = cartService.getCartItems();
        cartItems = cartItems.stream()
                .sorted(Comparator.comparing(item -> item.getPhone().getId()))
                .collect(Collectors.toList());
        assertEquals(EXISTING_PHONE_IDS[0], cartItems.get(0).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[1], cartItems.get(1).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[2], cartItems.get(2).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[3], cartItems.get(3).getPhone().getId());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_2[0], cartItems.get(0).getQuantity());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_2[1], cartItems.get(1).getQuantity());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_1[2], cartItems.get(2).getQuantity());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_2[3], cartItems.get(3).getQuantity());
    }

    @Test
    public void testUpdatePhoneNotInCart() {
        try {
            cartService.update(Stream.of(new Long[][]{{1001L, 1L}}).collect(Collectors.toMap(ar -> ar[0], ar -> ar[1])));
            fail();
        } catch (NoSuchPhoneException ignored) {}
    }

    @Test
    public void testUpdatePhoneTooBigQuantity() {
        cartService.addPhone(1001L, 1L);
        try {
            cartService.update(Stream.of(new Long[][]{{1001L, 1000L}}).collect(Collectors.toMap(ar -> ar[0], ar -> ar[1])));
            fail();
        } catch (TooBigQuantityException ignored) {}
    }

    @Test
    public void testRemove() {
        cartService.addPhone(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.addPhone(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.addPhone(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITIES_1[2]);
        cartService.remove(EXISTING_PHONE_IDS[0]);
        List<CartItem> items = cartService.getCartItems();
        items = items.stream().sorted(Comparator.comparing(item -> item.getPhone().getId())).collect(Collectors.toList());
        assertEquals(EXISTING_PHONE_IDS[1], items.get(0).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[2], items.get(1).getPhone().getId());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_1[1], items.get(0).getQuantity());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_1[2], items.get(1).getQuantity());
    }

    @Test
    public void testRemovePhoneNotInCart() {
        try {
            cartService.remove(EXISTING_PHONE_IDS[0]);
            fail();
        } catch (NoSuchPhoneException ignored) {}
    }
}
