package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static util.PhoneUtils.createPhone;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @InjectMocks
    private HttpSessionCartService httpSessionCartService = new HttpSessionCartService();

    @Mock
    private Cart cartMock;

    @Mock
    private PhoneService phoneService;

    @Mock
    private HttpSession httpSessionMock;

    private List<Phone> phoneList;
    private static final int PHONE_COUNT = 5;
    private static final String ATTRIBUTE_CART = "cart";
    private static final long NONEXISTENT_PHONE = -1;

    @Before
    public void initMocks() {
        initPhoneList();
        initPhoneServiceMock();
        initCartMock();
        when(httpSessionMock.getAttribute(ATTRIBUTE_CART)).thenReturn(cartMock);
    }

    private void initPhoneList() {
        phoneList = new ArrayList<>();
        for (int i = 1; i <= PHONE_COUNT; i++) {
            phoneList.add(createPhone(i));
        }
    }

    private void initPhoneServiceMock() {
        phoneList.forEach(phone -> when(phoneService.get(phone.getId())).thenReturn(Optional.of(phone)));
        when(phoneService.get(NONEXISTENT_PHONE)).thenReturn(Optional.empty());
    }

    private void initCartMock() {
        when(cartMock.getItems()).thenReturn(new HashMap<>());
    }

    @Test
    public void checkAddOnePhoneTwice() {
        final long COUNT_PHONES = 1;
        final Long QUANTITY = 2L;
        Phone phone = phoneList.get(0);

        assertTrue(cartMock.getItems().isEmpty());

        httpSessionCartService.addPhone(phone.getId(), 1L);
        httpSessionCartService.addPhone(phone.getId(), 1L);
        assertEquals(COUNT_PHONES, cartMock.getItems().size());
        assertTrue(cartMock.getItems().keySet().contains(phone));
        assertEquals(QUANTITY, cartMock.getItems().get(phone));
    }

    @Test
    public void checkAddTwoPhones() {
        final long COUNT_PHONES = 2;
        final long QUANTITY = 2L;
        List<Phone> phonesForAdd = new ArrayList<Phone>() {{
            add(phoneList.get(0));
            add(phoneList.get(1));
        }};

        assertTrue(cartMock.getItems().isEmpty());

        httpSessionCartService.addPhone(phonesForAdd.get(0).getId(), 1L);
        httpSessionCartService.addPhone(phonesForAdd.get(1).getId(), 1L);
        assertEquals(COUNT_PHONES, cartMock.getItems().size());
        checkPhonesContainInCart(phonesForAdd, cartMock.getItems());
        assertEquals(QUANTITY, httpSessionCartService.getCountItems());
    }

    @Test
    public void checkAddOnePhone() {
        final long COUNT_PHONES = 1;
        final Long QUANTITY = 2L;
        Phone phone = phoneList.get(0);

        assertTrue(cartMock.getItems().isEmpty());

        httpSessionCartService.addPhone(phone.getId(), 2L);
        assertEquals(COUNT_PHONES, cartMock.getItems().size());
        assertTrue(cartMock.getItems().keySet().contains(phone));
        assertEquals(QUANTITY, cartMock.getItems().get(phone));
    }

    @Test(expected = NoSuchElementException.class)
    public void checkAddNonexistentPhone() {
        httpSessionCartService.addPhone(NONEXISTENT_PHONE, 1L);
    }

    @Test
    public void checkAddTwoPhoneThenRemoveOne() {
        final long COUNT_PHONES = 1;
        List<Phone> phonesForAdd = new ArrayList<Phone>() {{
            add(phoneList.get(0));
            add(phoneList.get(1));
        }};

        httpSessionCartService.addPhone(phonesForAdd.get(0).getId(), 1L);
        httpSessionCartService.addPhone(phonesForAdd.get(1).getId(), 1L);
        checkPhonesContainInCart(phonesForAdd, cartMock.getItems());

        httpSessionCartService.remove(phonesForAdd.get(0).getId());
        assertEquals(COUNT_PHONES, cartMock.getItems().size());
        assertTrue(cartMock.getItems().keySet().contains(phonesForAdd.get(1)));
    }

    @Test
    public void checkAddPhoneThenRemoveThenAdd() {
        final long COUNT_PHONES = 1;
        Phone phone = phoneList.get(0);

        httpSessionCartService.addPhone(phone.getId(), 1L);
        assertTrue(cartMock.getItems().keySet().contains(phone));
        httpSessionCartService.remove(phone.getId());
        assertTrue(cartMock.getItems().isEmpty());
        httpSessionCartService.addPhone(phone.getId(), 1L);
        assertEquals(COUNT_PHONES, cartMock.getItems().size());
        assertTrue(cartMock.getItems().keySet().contains(phone));
    }

    @Test(expected = NoSuchElementException.class)
    public void checkRemoveNonexistentPhone() {
        httpSessionCartService.remove(NONEXISTENT_PHONE);
    }

    @Test
    public void checkUpdateOnePhoneWhenTwoInCart() {
        final long COUNT_PHONES = 2;
        final long QUANTITY = 3L;
        List<Phone> phonesForAdd = new ArrayList<Phone>() {{
            add(phoneList.get(0));
            add(phoneList.get(1));
        }};
        Map<Long, Long> items = new HashMap<Long, Long>() {{put(1L, 2L);}};

        httpSessionCartService.addPhone(phonesForAdd.get(0).getId(), 1L);
        httpSessionCartService.addPhone(phonesForAdd.get(1).getId(), 1L);
        checkPhonesContainInCart(phonesForAdd, cartMock.getItems());

        httpSessionCartService.update(items);
        assertEquals(COUNT_PHONES, cartMock.getItems().size());
        checkPhonesContainInCart(phonesForAdd, cartMock.getItems());
        assertEquals(QUANTITY, httpSessionCartService.getCountItems());
    }

    @Test(expected = NoSuchElementException.class)
    public void checkUpdateNonexistentPhone() {
        Map<Long, Long> items = new HashMap<Long, Long>(){{put(NONEXISTENT_PHONE, 1L);}};
        httpSessionCartService.update(items);
    }

    @Test
    public void checkItemsCountWhenAddTwoPhone() {
        final long QUANTITY = 2L;
        List<Phone> phonesForAdd = new ArrayList<Phone>() {{
            add(phoneList.get(0));
            add(phoneList.get(1));
        }};

        httpSessionCartService.addPhone(1L, 1L);
        httpSessionCartService.addPhone(2L, 1L);
        checkPhonesContainInCart(phonesForAdd, cartMock.getItems());
        assertEquals(QUANTITY, httpSessionCartService.getCountItems());
    }

    @Test
    public void checkItemsCountWhenOnePhoneTwice() {
        final long QUANTITY = 2L;
        httpSessionCartService.addPhone(1L, 1L);
        httpSessionCartService.addPhone(1L, 1L);
        assertEquals(QUANTITY, httpSessionCartService.getCountItems());
    }

    @Test
    public void checkItemsCountWhenNoPhoneInCart() {
        assertEquals(0, httpSessionCartService.getCountItems());
    }

    @Test
    public void checkGetItems() {
        Map<Phone, Long> items = phoneList.stream().collect(Collectors.toMap(phone -> phone, phone -> 1L));
        phoneList.forEach(phone -> httpSessionCartService.addPhone(phone.getId(), 1L));
        assertEquals(items, httpSessionCartService.getAllItems());
    }

    private void checkPhonesContainInCart(List<Phone> phonesForAdd, Map<Phone, Long> items) {
        List phones = items.keySet().stream()
                .filter(phone -> !phonesForAdd.contains(phone))
                .collect(toList());
        assertTrue(phones.isEmpty());
    }
}