package com.es.test.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.service.CartServiceImpl;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.core.phone.dao.PhoneDao;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.dao.StockDao;
import com.es.phoneshop.core.stock.model.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@Transactional
public class CartServiceTest {
    @Mock
    private Cart cart;
    @Mock
    private PhoneDao phoneDao;
    @Mock
    private StockDao stockDao;
    @InjectMocks
    private CartService cartService = new CartServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCartStatus() {
        Phone phone1 = new Phone();
        phone1.setPrice(BigDecimal.valueOf(200.50));

        Phone phone2 = new Phone();
        phone2.setPrice(BigDecimal.valueOf(99));

        Phone phone3 = new Phone();
        phone3.setPrice(BigDecimal.valueOf(59));

        when(cart.getItems()).thenReturn(Stream.of(
                new CartItem(phone1, 4L),
                new CartItem(phone2, 3L),
                new CartItem(phone3, 5L)
        ).collect(Collectors.toList()));

        CartStatus cartStatus = cartService.getCartStatus();
        assertEquals((Long) 12L, cartStatus.getPhonesTotal());
        assertTrue(cartStatus.getCostTotal().compareTo(BigDecimal.valueOf(1394)) == 0);
    }

    @Test
    public void testGetCartStatusWhenEmpty() {
        when(cart.getItems()).thenReturn(new ArrayList<>());
        CartStatus cartStatus = cartService.getCartStatus();
        assertEquals((Long) 0L, cartStatus.getPhonesTotal());
        assertTrue(cartStatus.getCostTotal().compareTo(BigDecimal.ZERO) == 0);
    }

    @Test
    public void testAddPhone() {
        Phone phone = new Phone();
        phone.setId(1001L);
        Stock stock = new Stock();
        stock.setPhone(phone);
        stock.setStock(5);
        when(phoneDao.get(1001L)).thenReturn(Optional.of(phone));
        when(stockDao.get(phone)).thenReturn(Optional.of(stock));
        cartService.addPhone(1001L, 3L);
    }

    @Test
    public void testAddPhoneWithTooBigQuantity() {
        Phone phone = new Phone();
        phone.setId(1001L);
        Stock stock = new Stock();
        stock.setPhone(phone);
        stock.setStock(5);
        when(phoneDao.get(1001L)).thenReturn(Optional.of(phone));
        when(stockDao.get(phone)).thenReturn(Optional.of(stock));
        try {
            cartService.addPhone(1001L, 10L);
            fail();
        } catch (TooBigQuantityException ignored) {}
    }

    @Test
    public void testUpdate() {
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Stock stock1 = new Stock();
        stock1.setPhone(phone1);
        stock1.setStock(10);

        Phone phone2 = new Phone();
        phone2.setId(1002L);
        Stock stock2 = new Stock();
        stock2.setPhone(phone2);
        stock2.setStock(9);

        Phone phone3 = new Phone();
        phone3.setId(1003L);
        Stock stock3 = new Stock();
        stock3.setPhone(phone3);
        stock3.setStock(12);

        Object[][] arr = {{phone1, 5L}, {phone2, 3L}, {phone3, 4L}};
        when(cart.getItems()).thenReturn(Stream.of(arr).map(ar -> new CartItem((Phone) ar[0], (Long) ar[1])).collect(Collectors.toList()));
        when(stockDao.get(phone1)).thenReturn(Optional.of(stock1));
        when(stockDao.get(phone2)).thenReturn(Optional.of(stock2));
        when(stockDao.get(phone3)).thenReturn(Optional.of(stock3));

        Long[][] updateArr = {{1001L, 7L}, {1003L, 5L}};
        try {
            cartService.update(Stream.of(updateArr).collect(Collectors.toMap(ar -> ar[0], ar -> ar[1])));
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testUpdatePhoneNotInCart() {
        when(cart.getItems()).thenReturn(new ArrayList<>());
        try {
            Map<Long, Long> updateMap = new HashMap<>();
            updateMap.put(1001L, 5L);
            cartService.update(updateMap);
            fail();
        } catch (NoSuchPhoneException ignored) {}
    }

    @Test
    public void testUpdateTooBigQuantity() {
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Stock stock1 = new Stock();
        stock1.setPhone(phone1);
        stock1.setStock(10);
        when(cart.getItems()).thenReturn(Stream.of(phone1)
                .map(phone -> new CartItem(phone, 1L))
                .collect(Collectors.toList()));
        when(stockDao.get(phone1)).thenReturn(Optional.of(stock1));
        try {
            Map<Long, Long> updateMap = new HashMap<>();
            updateMap.put(1001L, 100L);
            cartService.update(updateMap);
            fail();
        } catch (TooBigQuantityException ignored) {}
    }

    @Test
    public void testUpdateNoStock() {
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        when(cart.getItems()).thenReturn(Stream.of(new CartItem(phone1, 5L)).collect(Collectors.toList()));
        when(stockDao.get(phone1)).thenReturn(Optional.empty());
        try {
            Map<Long, Long> updateMap = new HashMap<>();
            updateMap.put(1001L, 3L);
            cartService.update(updateMap);
            fail();
        } catch (NoStockFoundException ignored) {}
    }

    @Test
    public void testGetCartItems() {
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        Phone phone3 = new Phone();
        phone3.setId(1003L);
        List<CartItem> expectedItems = Stream
                .of(new CartItem(phone1, 5L), new CartItem(phone2, 2L), new CartItem(phone3, 4L))
                .collect(Collectors.toList());
        when(cart.getItems()).thenReturn(expectedItems);
        List<CartItem> actualItems = cartService.getCartItems();
        for (int i = 0; i < 3; i++) {
            assertSame(expectedItems.get(i).getPhone(), actualItems.get(i).getPhone());
            assertSame(expectedItems.get(i).getQuantity(), actualItems.get(i).getQuantity());
        }
    }

    @Test
    public void testRemove() {
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        Phone phone3 = new Phone();
        phone3.setId(1003L);
        List<CartItem> expectedItems = Stream
                .of(new CartItem(phone1, 5L), new CartItem(phone2, 2L), new CartItem(phone3, 4L))
                .collect(Collectors.toList());
        when(cart.getItems()).thenReturn(expectedItems);
        try {
            cartService.remove(1001L);
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testRemovePhoneNotInCart() {
        when(cart.getItems()).thenReturn(new ArrayList<>());
        try {
            cartService.remove(1001L);
            fail();
        } catch (NoSuchPhoneException ignored) {}
    }
}
