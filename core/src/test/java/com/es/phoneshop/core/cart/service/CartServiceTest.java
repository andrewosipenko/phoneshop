package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.OutOfStockException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.phone.service.PhoneService;
import com.es.phoneshop.core.stock.model.Stock;
import com.es.phoneshop.core.stock.service.StockService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CartServiceTest {
    @Mock
    private Cart cart;
    @Mock
    private PhoneService phoneService;
    @Mock
    private StockService stockService;
    @InjectMocks
    private CartService cartService = new CartServiceImpl();

    private static Phone phone1;
    private static Phone phone2;
    private static Phone phone3;
    private static Stock stock1;
    private static Stock stock2;
    private static Stock stock3;

    @BeforeClass
    public static void initPhonesAndStocks() {
        phone1 = new Phone();
        phone2 = new Phone();
        phone3 = new Phone();
        phone1.setId(1001L);
        phone2.setId(1002L);
        phone3.setId(1003L);
        phone1.setPrice(BigDecimal.valueOf(200));
        phone2.setPrice(BigDecimal.valueOf(300));
        phone3.setPrice(BigDecimal.valueOf(150.50));

        stock1 = new Stock();
        stock2 = new Stock();
        stock3 = new Stock();
        stock1.setPhone(phone1);
        stock2.setPhone(phone2);
        stock3.setPhone(phone3);
        stock1.setStock(5L);
        stock2.setStock(7L);
        stock3.setStock(4L);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() {
        when(phoneService.getPhone(phone1.getId())).thenReturn(Optional.of(phone1));
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        cartService.add(phone1.getId(), 3L);
    }

    @Test
    public void testAddMoreOfExisting() {
        when(phoneService.getPhone(phone1.getId())).thenReturn(Optional.of(phone1));
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        when(cart.getCartItems()).thenReturn(Stream.of(new CartItem(phone1, 2L)).collect(Collectors.toList()));
        cartService.add(1001L, 3L);
    }

    @Test(expected = TooBigQuantityException.class)
    public void testAddWithTooBigQuantity() {
        when(phoneService.getPhone(phone1.getId())).thenReturn(Optional.of(phone1));
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        cartService.add(1001L, 10L);
    }

    @Test
    public void testUpdate() {
        Object[][] arr = {{phone1, 5L}, {phone2, 3L}, {phone3, 4L}};
        when(cart.getCartItems()).thenReturn(Stream.of(arr).map(ar -> new CartItem((Phone) ar[0], (Long) ar[1])).collect(Collectors.toList()));
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        when(stockService.getStock(phone2)).thenReturn(Optional.of(stock2));
        when(stockService.getStock(phone3)).thenReturn(Optional.of(stock3));

        Long[][] updateArr = {{phone1.getId(), 3L}, {phone3.getId(), 2L}};
        cartService.update(Stream.of(updateArr).collect(Collectors.toMap(ar -> ar[0], ar -> ar[1])));
    }

    @Test(expected = NoSuchPhoneException.class)
    public void testUpdatePhoneNotInCart() {
        when(cart.getCartItems()).thenReturn(new ArrayList<>());
        Map<Long, Long> updateMap = new HashMap<>();
        updateMap.put(phone1.getId(), 5L);
        cartService.update(updateMap);
    }

    @Test(expected = TooBigQuantityException.class)
    public void testUpdateTooBigQuantity() {
        when(cart.getCartItems()).thenReturn(Stream.of(phone1)
                .map(phone -> new CartItem(phone, 1L))
                .collect(Collectors.toList()));
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        Map<Long, Long> updateMap = new HashMap<>();
        updateMap.put(phone1.getId(), 100L);
        cartService.update(updateMap);
    }

    @Test(expected = NoStockFoundException.class)
    public void testUpdateNoStock() {
        when(cart.getCartItems()).thenReturn(Stream.of(new CartItem(phone1, 5L)).collect(Collectors.toList()));
        when(stockService.getStock(phone1)).thenReturn(Optional.empty());
        Map<Long, Long> updateMap = new HashMap<>();
        updateMap.put(phone1.getId(), 3L);
        cartService.update(updateMap);
    }

    @Test
    public void testRemove() {
        List<CartItem> expectedItems = Stream
                .of(new CartItem(phone1, 5L), new CartItem(phone2, 2L), new CartItem(phone3, 4L))
                .collect(Collectors.toList());
        when(cart.getCartItems()).thenReturn(expectedItems);
        cartService.remove(phone1.getId());
    }

    @Test(expected = NoSuchPhoneException.class)
    public void testRemovePhoneNotInCart() {
        when(cart.getCartItems()).thenReturn(new ArrayList<>());
        cartService.remove(phone1.getId());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testClear() {
        List<CartItem> cartItemsMock = mock(List.class);
        when(cart.getCartItems()).thenReturn(cartItemsMock);
        cartService.clear();
        verify(cartItemsMock).clear();
    }

    @Test
    public void testGetCart() {
        assertSame(cart, cartService.getCart());
    }

    @Test
    public void testValidateStocksAndRemoveOdd() {
        Object[][] arr = {{phone1, 5L}, {phone2, 12L}, {phone3, 4L}};
        List<CartItem> cartItems = Stream.of(arr).map(ar -> new CartItem((Phone) ar[0], (Long) ar[1])).collect(Collectors.toList());
        when(cart.getCartItems()).thenReturn(cartItems);
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        when(stockService.getStock(phone2)).thenReturn(Optional.of(stock2));
        when(stockService.getStock(phone3)).thenReturn(Optional.of(stock3));

        try {
            cartService.validateStocksAndRemoveOdd();
            fail();
        } catch (OutOfStockException e) {
            List<Phone> rejected = e.getRejectedPhones();
            assertEquals(1, rejected.size());
            assertSame(phone2, rejected.get(0));
        }
        assertEquals(2, cartItems.size());
        assertSame(phone1, cartItems.get(0).getPhone());
        assertSame(phone3, cartItems.get(1).getPhone());
    }

    @Test
    public void testValidateStocksAndRemoveOddAllInStock() {
        Object[][] arr = {{phone1, 5L}, {phone2, 3L}, {phone3, 4L}};
        List<CartItem> cartItems = Stream.of(arr).map(ar -> new CartItem((Phone) ar[0], (Long) ar[1])).collect(Collectors.toList());
        when(cart.getCartItems()).thenReturn(cartItems);
        when(stockService.getStock(phone1)).thenReturn(Optional.of(stock1));
        when(stockService.getStock(phone2)).thenReturn(Optional.of(stock2));
        when(stockService.getStock(phone3)).thenReturn(Optional.of(stock3));
        cartService.validateStocksAndRemoveOdd();

        assertEquals(3, cartItems.size());
        assertSame(phone1, cartItems.get(0).getPhone());
        assertSame(phone2, cartItems.get(1).getPhone());
        assertSame(phone3, cartItems.get(2).getPhone());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRecountTotal() {
        Object[][] arr = {{phone1, 5L}, {phone2, 3L}, {phone3, 4L}};
        when(cart.getCartItems()).thenReturn(Stream.of(arr).map(ar -> new CartItem((Phone) ar[0], (Long) ar[1])).collect(Collectors.toList()));
        ArgumentCaptor<BigDecimal> captor = ArgumentCaptor.forClass(BigDecimal.class);
        cartService.update(Collections.EMPTY_MAP);
        verify(cart).setSubtotal(captor.capture());
        BigDecimal expected = (BigDecimal.valueOf(5).multiply(phone1.getPrice()))
                .add(BigDecimal.valueOf(3).multiply(phone2.getPrice()))
                .add(BigDecimal.valueOf(4).multiply(phone3.getPrice()));
        assertEquals(0, captor.getValue().compareTo(expected));
    }
}
