package com.es.core.cart;

import com.es.core.AbstractTest;
import com.es.core.dao.phone.PhoneDao;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest extends AbstractTest {

    @InjectMocks
    private CartService cartService = new HttpSessionCartService();

    @Mock
    private Cart mockCart;

    @Mock
    private PhoneDao mockPhoneDao;

    private List<Phone> phoneList;

    private static final int COUNT_PHONE = 5;

    private Phone phoneWithoutPrice;

    private List<CartItem> cartItemsList;

    @Before
    public void init() {
        initPhoneList();
        initMockPhoneList();
        initMockCart();
    }

    private void initPhoneList() {
        phoneList = new ArrayList<>();
        for (int i = 0; i < COUNT_PHONE - 1; i++) {
            Phone newPhone = createPhone("test" + Integer.toString(i), "test" + Integer.toString(i), (long) i, i);
            phoneList.add(newPhone);
        }
        phoneWithoutPrice = createPhone("noPrice", "noPrice", (long) (COUNT_PHONE - 1), 1);
        phoneWithoutPrice.setPrice(null);
        phoneList.add(phoneWithoutPrice);
    }

    private void initMockPhoneList() {
        when(mockPhoneDao.get(isA(Long.class))).thenReturn(Optional.empty());
        for (Phone phone : phoneList) {
            when(mockPhoneDao.get(phone.getId())).thenReturn(Optional.of(phone));
        }
    }

    private void initMockCart() {
        cartItemsList = new ArrayList<>();
        when(mockCart.getItems()).thenReturn(cartItemsList);
    }

    @After
    public void resetPhoneMockDao() {
        reset(mockCart);

        try {
            verifyNoMoreInteractions(mockPhoneDao);
        } finally {
            reset(mockPhoneDao);
        }
    }


    @Test
    public void addToCartPhone() throws PhoneNotFoundException {
        final Long QUANTITY = 1L;
        final int COUNT_ITEMS = 1;

        Phone phone = phoneList.get(0);

        cartService.addPhone(phone.getId(), QUANTITY);

        verify(mockPhoneDao).get(eq(phone.getId()));

        verify(mockCart).setCost(eq(phone.getPrice()));

        assertEquals(COUNT_ITEMS, cartItemsList.size());
    }

    @Test
    public void addToCartPhoneSeveralTimes() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;
        final int COUNT_ITEMS = 1;

        Phone phone = phoneList.get(0);

        final BigDecimal FIRST_COST = phone.getPrice().multiply(new BigDecimal(QUANTITY_1));

        final BigDecimal SECOND_COST = phone.getPrice().multiply(new BigDecimal(QUANTITY_1 + QUANTITY_2));

        cartService.addPhone(phone.getId(), QUANTITY_1);
        cartService.addPhone(phone.getId(), QUANTITY_2);

        verify(mockPhoneDao, times(2)).get(eq(phone.getId()));

        verify(mockCart).setCost(FIRST_COST);

        verify(mockCart).setCost(SECOND_COST);

        assertEquals(COUNT_ITEMS, cartItemsList.size());
    }

    @Test
    public void addToCartSeveralPhones() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;
        final int COUNT_ITEMS = 2;

        Phone phone1 = phoneList.get(0);
        Phone phone2 = phoneList.get(1);

        final BigDecimal FIRST_COST = phone1.getPrice().multiply(new BigDecimal(QUANTITY_1));

        final BigDecimal SECOND_COST = phone2.getPrice().multiply(new BigDecimal(QUANTITY_2)).add(FIRST_COST);

        cartService.addPhone(phone1.getId(), QUANTITY_1);
        cartService.addPhone(phone2.getId(), QUANTITY_2);

        verify(mockPhoneDao).get(eq(phone1.getId()));
        verify(mockPhoneDao).get(eq(phone2.getId()));

        verify(mockCart).setCost(FIRST_COST);

        verify(mockCart).setCost(SECOND_COST);
        assertEquals(COUNT_ITEMS, cartItemsList.size());
    }

    @Test(expected = PhoneNotFoundException.class)
    public void addNonexistentPhone() throws PhoneNotFoundException {
        final Long NONEXISTENT_PHONE = -1L;
        final Long QUANTITY = 1L;

        try {
            cartService.addPhone(NONEXISTENT_PHONE, QUANTITY);
        } finally {
            verify(mockPhoneDao).get(eq(NONEXISTENT_PHONE));
        }
    }

    @Test
    public void removePhone() throws PhoneNotFoundException {
        List<CartItem> cartItems = initNonemptyMockCart();

        Phone addedPhone = phoneList.get(0);

        cartItems = cartItems.stream().filter(cartItem -> !cartItem.getPhone().equals(addedPhone))
                .collect(Collectors.toList());

        cartService.updateOrDelete(addedPhone.getId(), 0L);

        verify(mockCart, atLeastOnce()).getItems();
        verify(mockCart).setCost(getCost(cartItems));
    }

    @Test
    public void updatePhoneQuantity() throws PhoneNotFoundException {
        final Long NEW_QUANTITY = 5L;

        List<CartItem> cartItems = initNonemptyMockCart();

        Phone addedPhone = phoneList.get(0);

        cartItems.stream().filter(cartItem -> !cartItem.getPhone().equals(addedPhone))
                .forEach(cartItem -> cartItem.setQuantity(NEW_QUANTITY));

        cartService.updateOrDelete(addedPhone.getId(), NEW_QUANTITY);

        verify(mockCart, atLeastOnce()).getItems();
        verify(mockCart).setCost(getCost(cartItems));
    }


    @Test
    public void updateOrDeleteNotAddedPhone() {
        List<CartItem> cartItems = initNonemptyMockCart();

        Phone notAddedPhone = phoneList.get(3);

        cartService.updateOrDelete(notAddedPhone.getId(), 0L);

        verify(mockCart, atLeastOnce()).getItems();
        verify(mockCart).setCost(getCost(cartItems));
    }

    @Test
    public void updateCart() throws PhoneNotFoundException {
        List<CartItem> cartItems = initNonemptyMockCart();

        List<CartItem> updatedCartItem = cartItems.stream()
                .map(cartItem -> new CartItem(cartItem.getPhone(), cartItem.getQuantity() * 2L))
                .collect(Collectors.toList());

        updatedCartItem.get(0).setQuantity(0L);

        Map<Long, Long> mapPhoneId = updatedCartItem.stream()
                .collect(Collectors.toMap(item -> item.getPhone().getId(), CartItem::getQuantity));

        cartService.update(mapPhoneId);

        verify(mockCart, atLeastOnce()).getItems();
        verify(mockCart).setCost(getCost(updatedCartItem));
    }

    @Test(expected = IllegalStateException.class)
    public void addPhoneWithoutPrice() throws PhoneNotFoundException {
        final Long QUANTITY = 1L;

        try {
            cartService.addPhone(phoneWithoutPrice.getId(), QUANTITY);
        } finally {
            verify(mockPhoneDao).get(eq(phoneWithoutPrice.getId()));
        }
    }

    @Test
    public void deleteOutOfStockCheck() {
        List<CartItem> cartItems = initNonemptyMockCart();
        List<Long> phoneStock = cartItems.stream()
                .map(cartItem -> cartItem.getPhone().getId())
                .collect(Collectors.toList());

        List<Stock> stockList = phoneStock.stream()
                .map(phoneId -> new Stock(phoneId, 0, 0))
                .collect(Collectors.toList());

        when(mockPhoneDao.getStocks(phoneStock)).thenReturn(stockList);

        cartService.deleteOutOfStock();

        verify(mockPhoneDao).getStocks(eq(phoneStock));
        verify(mockCart, atLeastOnce()).getItems();
        verify(mockCart).setCost(BigDecimal.ZERO);
    }

    private List<CartItem> initNonemptyMockCart() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(phoneList.get(0), 1L));
        items.add(new CartItem(phoneList.get(1), 2L));
        items.add(new CartItem(phoneList.get(2), 3L));
        when(mockCart.getItems()).thenReturn(items);
        return items;
    }


    private BigDecimal getCost(List<CartItem> items) {
        BigDecimal cost = BigDecimal.ZERO;
        for (CartItem item : items) {
            cost = cost.add(item.getPhone().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return cost;
    }
}
