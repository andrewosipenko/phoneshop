package com.es.core.cart;

import com.es.core.AbstractTest;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
        when(mockCart.getItems()).thenReturn(Collections.EMPTY_MAP);
    }

    @After
    public void resetPhoneMockDao() {
        try {
            verifyNoMoreInteractions(mockCart);
        } finally {
            reset(mockCart);
        }

        try {
            verifyNoMoreInteractions(mockPhoneDao);
        } finally {
            reset(mockPhoneDao);
        }
    }


    @Test
    public void addToCartPhone() throws PhoneNotFoundException {
        final Long QUANTITY = 1L;

        Phone phone = phoneList.get(0);

        cartService.addPhone(phone.getId(), QUANTITY);

        verify(mockPhoneDao).get(eq(phone.getId()));

        InOrder inOrder = inOrder(mockCart);

        inOrder.verify(mockCart).addPhone(eq(phone), eq(QUANTITY));
        inOrder.verify(mockCart).getItems();
        inOrder.verify(mockCart).setCost(any(BigDecimal.class));
    }

    @Test
    public void addToCartPhoneSeveralTimes() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;

        Phone phone = phoneList.get(0);

        cartService.addPhone(phone.getId(), QUANTITY_1);
        cartService.addPhone(phone.getId(), QUANTITY_2);

        verify(mockPhoneDao, times(2)).get(eq(phone.getId()));

        InOrder inOrder = inOrder(mockCart);

        inOrder.verify(mockCart).addPhone(eq(phone), eq(QUANTITY_1));
        inOrder.verify(mockCart).getItems();
        inOrder.verify(mockCart).setCost(any(BigDecimal.class));

        inOrder.verify(mockCart).addPhone(eq(phone), eq(QUANTITY_2));
        inOrder.verify(mockCart).getItems();
        inOrder.verify(mockCart).setCost(any(BigDecimal.class));
    }

    @Test
    public void addToCartSeveralPhones() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;

        Phone phone1 = phoneList.get(0);
        Phone phone2 = phoneList.get(1);

        cartService.addPhone(phone1.getId(), QUANTITY_1);
        cartService.addPhone(phone2.getId(), QUANTITY_2);

        verify(mockPhoneDao).get(eq(phone1.getId()));
        verify(mockPhoneDao).get(eq(phone2.getId()));

        InOrder inOrder = inOrder(mockCart);

        inOrder.verify(mockCart).addPhone(eq(phone1), eq(QUANTITY_1));
        inOrder.verify(mockCart).getItems();
        inOrder.verify(mockCart).setCost(any(BigDecimal.class));

        inOrder.verify(mockCart).addPhone(eq(phone2), eq(QUANTITY_2));
        inOrder.verify(mockCart).getItems();
        inOrder.verify(mockCart).setCost(any(BigDecimal.class));
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
    public void updateCart() throws PhoneNotFoundException {
        Map<Phone, Long> items = initNonemptyMockCart();

        Map<Long, Long> mapPhoneId = items.entrySet().stream()
                .collect(Collectors.toMap(o -> o.getKey().getId(), Map.Entry::getValue));

        List<Long> updatedPhones = new ArrayList<>(mapPhoneId.keySet());

        when(mockPhoneDao.getPhonesByIdList(updatedPhones)).thenReturn(new ArrayList<>(items.keySet()));

        cartService.update(mapPhoneId);

        verify(mockPhoneDao).getPhonesByIdList(eq(updatedPhones));

        InOrder inOrder = inOrder(mockCart);

        inOrder.verify(mockCart).setItems(items);
        inOrder.verify(mockCart, atLeastOnce()).getItems();
        inOrder.verify(mockCart).setCost(any(BigDecimal.class));
    }

    @Test
    public void removePhone() throws PhoneNotFoundException {
        initNonemptyMockCart();

        Phone addedPhone = phoneList.get(0);

        cartService.remove(addedPhone.getId());

        verify(mockCart, atLeastOnce()).getItems();
        verify(mockCart).setCost(any(BigDecimal.class));
    }

    @Test
    public void removeNotAddedPhone() throws PhoneNotFoundException {
        initNonemptyMockCart();

        Phone notAddedPhone = phoneList.get(3);

        cartService.remove(notAddedPhone.getId());

        verify(mockCart, atLeastOnce()).getItems();
    }

    @Test(expected = IllegalStateException.class)
    public void addPhoneWithoutPrice() throws PhoneNotFoundException {
        final Long QUANTITY = 1L;

        try {
            cartService.addPhone(phoneWithoutPrice.getId(), QUANTITY);
        } finally {
            verify(mockPhoneDao).get(ArgumentMatchers.eq(phoneWithoutPrice.getId()));
        }
    }

    @Test
    public void checkCost() throws PhoneNotFoundException {
        Map<Phone, Long> items = initNonemptyMockCart();

        Map<Long, Long> mapPhoneId = items.entrySet().stream()
                .collect(Collectors.toMap(o -> o.getKey().getId(), Map.Entry::getValue));

        List<Long> updatedPhones = new ArrayList<>(mapPhoneId.keySet());

        when(mockPhoneDao.getPhonesByIdList(updatedPhones)).thenReturn(new ArrayList<>(items.keySet()));

        cartService.update(mapPhoneId);

        BigDecimal cost = getCost(items);

        verify(mockPhoneDao).getPhonesByIdList(updatedPhones);

        verify(mockCart, atLeastOnce()).getItems();
        verify(mockCart).setItems(items);
        verify(mockCart).setCost(cost);
    }

    private Map<Phone, Long> initNonemptyMockCart() {
        Map<Phone, Long> items = new HashMap<>();
        items.put(phoneList.get(0), 1L);
        items.put(phoneList.get(1), 2L);
        items.put(phoneList.get(2), 3L);
        when(mockCart.getItems()).thenReturn(items);
        return items;
    }


    private BigDecimal getCost(Map<Phone, Long> items) {
        BigDecimal cost = BigDecimal.ZERO;
        for (Phone phone : items.keySet()) {
            cost = cost.add(phone.getPrice().multiply(new BigDecimal(items.get(phone))));
        }
        return cost;
    }
}
