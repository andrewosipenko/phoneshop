package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.OutOfStockException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.core.phone.model.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    private static final Long[] EXISTING_PHONE_IDS = {1001L, 1002L, 1003L, 1004L}; // ascending
    private static final Long[] PHONE_ACCEPTABLE_QUANTITIES_1 = {5L, 9L, 2L, 4L};
    private static final Long[] PHONE_ACCEPTABLE_QUANTITIES_2 = {3L, 4L, 3L, 4L}; //quantities_1[i] + quantities_2[i] must not exceed stock of ith phone
    private static final BigDecimal P0Q1_P0Q2_P1Q1_TOTAL_COST = BigDecimal.valueOf(7103);
    private static final Long PHONE_WITH_NO_STOCK = 1006L;
    private static final Long PHONE_NON_EXISTING = 999L;
    private static final Long P0_TOO_MUCH_QUANTITY = 30L;

    @Test
    public void testAdd() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_2[0]);
        List<CartItem> records = cartService.getCart().getCartItems();
        assertEquals(2, records.size());
        assertEquals(EXISTING_PHONE_IDS[0], records.get(0).getPhone().getId());
        assertEquals((Long) (PHONE_ACCEPTABLE_QUANTITIES_1[0] + PHONE_ACCEPTABLE_QUANTITIES_2[0]), records.get(0).getQuantity());
        assertEquals(EXISTING_PHONE_IDS[1], records.get(1).getPhone().getId());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_1[1], records.get(1).getQuantity());

        assertEquals((Long) (PHONE_ACCEPTABLE_QUANTITIES_1[0] + PHONE_ACCEPTABLE_QUANTITIES_1[1] + PHONE_ACCEPTABLE_QUANTITIES_2[0]), (Long) cartService.getCart().getPhonesTotal());
        assertEquals(0, cartService.getCart().getSubtotal().compareTo(P0Q1_P0Q2_P1Q1_TOTAL_COST));
    }

    @Test(expected = NoStockFoundException.class)
    public void testAddNoStock() {
        cartService.add(PHONE_WITH_NO_STOCK, 5L);
    }

    @Test(expected = NoSuchPhoneException.class)
    public void testAddNotExisting() {
        cartService.add(PHONE_NON_EXISTING, 5L);
    }

    @Test(expected = TooBigQuantityException.class)
    public void testAddTooBigQuantity() {
        cartService.add(EXISTING_PHONE_IDS[0], P0_TOO_MUCH_QUANTITY);
    }

    @Test
    public void testUpdate() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITIES_1[2]);
        cartService.add(EXISTING_PHONE_IDS[3], PHONE_ACCEPTABLE_QUANTITIES_1[3]);
        Long[][] updateArray = {
                {EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_2[0]},
                {EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_2[1]},
                {EXISTING_PHONE_IDS[3], PHONE_ACCEPTABLE_QUANTITIES_2[3]}
        };
        Map<Long, Long> updateMap = Stream.of(updateArray)
                .collect(Collectors.toMap(ar -> ar[0], ar -> ar[1]));
        cartService.update(updateMap);
        List<CartItem> cartItems = cartService.getCart().getCartItems();
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

    @Test(expected = NoSuchPhoneException.class)
    public void testUpdateNotInCart() {
        cartService.update(Stream.of(new Long[][]{{EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]}}).collect(Collectors.toMap(ar -> ar[0], ar -> ar[1])));
    }

    @Test
    public void testUpdateTooBigQuantity() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        try {
            cartService.update(Stream.of(new Long[][]{{EXISTING_PHONE_IDS[0], P0_TOO_MUCH_QUANTITY}}).collect(Collectors.toMap(ar -> ar[0], ar -> ar[1])));
            fail();
        } catch (TooBigQuantityException e) {
            assertEquals(1, e.getPhoneIds().size());
            assertEquals(EXISTING_PHONE_IDS[0], e.getPhoneIds().iterator().next());
        }
    }

    @Test
    public void testRemove() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITIES_1[2]);
        cartService.remove(EXISTING_PHONE_IDS[0]);
        List<CartItem> records = cartService.getCart().getCartItems();
        records = records.stream().sorted(Comparator.comparing(item -> item.getPhone().getId())).collect(Collectors.toList());
        assertEquals(EXISTING_PHONE_IDS[1], records.get(0).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[2], records.get(1).getPhone().getId());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_1[1], records.get(0).getQuantity());
        assertEquals(PHONE_ACCEPTABLE_QUANTITIES_1[2], records.get(1).getQuantity());
    }

    @Test(expected = NoSuchPhoneException.class)
    public void testRemoveNotInCart() {
        cartService.remove(EXISTING_PHONE_IDS[0]);
    }

    @Test
    public void testClear() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITIES_1[2]);
        cartService.clear();
        assertEquals(0, cartService.getCart().getCartItems().size());
        assertEquals(0, cartService.getCart().getPhonesTotal());
        assertEquals(0, cartService.getCart().getSubtotal().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void testValidateStocksAndRemoveOdd() {
        Phone phone = new Phone();
        phone.setPrice(BigDecimal.valueOf(200));
        phone.setId(EXISTING_PHONE_IDS[0]);
        cartService.getCart().getCartItems().add(new CartItem(phone, P0_TOO_MUCH_QUANTITY));
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITIES_1[2]);
        try {
            cartService.validateStocksAndRemoveOdd();
        } catch (OutOfStockException e) {
            List<Phone> rejected = e.getRejectedPhones();
            assertEquals(1, rejected.size());
            assertSame(phone, rejected.get(0));
        }
        assertEquals(2, cartService.getCart().getCartItems().size());
        assertEquals(EXISTING_PHONE_IDS[1], cartService.getCart().getCartItems().get(0).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[2], cartService.getCart().getCartItems().get(1).getPhone().getId());
    }

    @Test
    public void testValidateStocksAndRemoveOddAllInStock() {
        cartService.add(EXISTING_PHONE_IDS[0], PHONE_ACCEPTABLE_QUANTITIES_1[0]);
        cartService.add(EXISTING_PHONE_IDS[1], PHONE_ACCEPTABLE_QUANTITIES_1[1]);
        cartService.add(EXISTING_PHONE_IDS[2], PHONE_ACCEPTABLE_QUANTITIES_1[2]);
        cartService.validateStocksAndRemoveOdd();
        assertEquals(3, cartService.getCart().getCartItems().size());
        assertEquals(EXISTING_PHONE_IDS[0], cartService.getCart().getCartItems().get(0).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[1], cartService.getCart().getCartItems().get(1).getPhone().getId());
        assertEquals(EXISTING_PHONE_IDS[2], cartService.getCart().getCartItems().get(2).getPhone().getId());
    }

    @Test
    public void testGetDeliveryPrice() {
        assertEquals(0, deliveryPrice.compareTo(cartService.getCart().getDeliveryPrice()));
    }
}