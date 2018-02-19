package com.es.core.cart;

import com.es.core.AbstractTest;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("phoneDaoMock")
@ContextConfiguration("classpath:context/testContext-core.xml")
public class HttpSessionCartServiceTest extends AbstractTest {

    @Resource
    private CartService cartService;

    @Resource
    private HttpSession httpSession;

    @Resource
    private PhoneDao mockPhoneDao;

    private List<Phone> phoneList;

    private static final int COUNT_PHONE = 5;

    private Phone phoneWithoutPrice;

    @Before
    public void initPhoneList() {
        phoneList = new ArrayList<>();
        for (int i = 0; i < COUNT_PHONE - 1; i++) {
            Phone newPhone = createPhone("test" + Integer.toString(i), "test" + Integer.toString(i), new Long(i), i);
            phoneList.add(newPhone);
        }
        phoneWithoutPrice = createPhone("noPrice", "noPrice", new Long(COUNT_PHONE - 1), 1);
        phoneWithoutPrice.setPrice(null);
        phoneList.add(phoneWithoutPrice);
    }

    @Before
    public void initMockPhoneDao() {
        when(mockPhoneDao.get(isA(Long.class))).thenReturn(Optional.empty());
        for (Phone phone : phoneList) {
            when(mockPhoneDao.get(phone.getId())).thenReturn(Optional.of(phone));
        }
    }

    @Before
    public void initSession() {
        httpSession.removeAttribute("cart");
        httpSession.removeAttribute("cartCost");
    }

    @After
    public void resetPhoneMockDao() {
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

        Cart cart = cartService.getCart();

        Assert.assertEquals(1, cart.getItems().size());
        Assert.assertEquals(QUANTITY, cart.getItems().get(phone.getId()));

        verify(mockPhoneDao).get(ArgumentMatchers.eq(phone.getId()));

        Map<Long, Long> items = new HashMap<>();
        items.put(phone.getId(), QUANTITY);
        checkCost(items);
    }

    @Test
    public void addToCartPhoneSeveralTimes() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;

        Phone phone = phoneList.get(0);

        cartService.addPhone(phone.getId(), QUANTITY_1);
        cartService.addPhone(phone.getId(), QUANTITY_2);

        Cart cart = cartService.getCart();

        Assert.assertEquals(1, cart.getItems().size());
        Assert.assertEquals(new Long(QUANTITY_1 + QUANTITY_2), cart.getItems().get(phone.getId()));

        verify(mockPhoneDao, times(2)).get(ArgumentMatchers.eq(phone.getId()));

        Map<Long, Long> items = new HashMap<>();
        items.put(phone.getId(), QUANTITY_1 + QUANTITY_2);
        checkCost(items);
    }

    @Test
    public void addToCartSeveralPhones() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;

        Phone phone1 = phoneList.get(0);
        Phone phone2 = phoneList.get(1);

        cartService.addPhone(phone1.getId(), QUANTITY_1);
        cartService.addPhone(phone2.getId(), QUANTITY_2);

        Cart cart = cartService.getCart();

        Assert.assertEquals(2, cart.getItems().size());
        Assert.assertEquals(QUANTITY_1, cart.getItems().get(phone1.getId()));
        Assert.assertEquals(QUANTITY_2, cart.getItems().get(phone2.getId()));

        verify(mockPhoneDao).get(ArgumentMatchers.eq(phone1.getId()));
        verify(mockPhoneDao).get(ArgumentMatchers.eq(phone2.getId()));

        Map<Long, Long> items = new HashMap<>();
        items.put(phone1.getId(), QUANTITY_1);
        items.put(phone2.getId(), QUANTITY_2);
        checkCost(items);
    }

    @Test(expected = PhoneNotFoundException.class)
    public void addNonexistentPhone() throws PhoneNotFoundException {
        final Long NONEXISTENT_PHONE = -1L;
        final Long QUANTITY = 1L;

        try {
            cartService.addPhone(NONEXISTENT_PHONE, QUANTITY);
        } finally {
            verify(mockPhoneDao).get(ArgumentMatchers.eq(NONEXISTENT_PHONE));
        }
    }

    @Test
    public void updateCart() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 3L;
        final Long QUANTITY_2 = 5L;

        Phone phone1 = phoneList.get(0);
        Phone phone2 = phoneList.get(1);


        Map<Long, Long> items = new HashMap<>();
        items.put(phone1.getId(), QUANTITY_1);
        items.put(phone2.getId(), QUANTITY_2);

        List<Long> updatedPhones = new ArrayList<>(items.keySet());

        when(mockPhoneDao.getPhonesByIdList(updatedPhones)).thenReturn(Arrays.asList(phone1, phone2));

        cartService.update(items);

        verify(mockPhoneDao).getPhonesByIdList(ArgumentMatchers.eq(updatedPhones));
        checkCost(items);
    }

    @Test
    public void removePhone() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;

        Phone phone1 = phoneList.get(0);
        Phone phone2 = phoneList.get(1);

        cartService.addPhone(phone1.getId(), QUANTITY_1);
        cartService.addPhone(phone2.getId(), QUANTITY_2);

        cartService.remove(phone1.getId());

        Cart cart = cartService.getCart();

        Assert.assertEquals(1, cart.getItems().size());
        Assert.assertEquals(QUANTITY_2, cart.getItems().get(phone2.getId()));

        InOrder inOrder = inOrder(mockPhoneDao);

        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone1.getId()));
        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone2.getId()));
        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone1.getId()));

        Map<Long, Long> items = new HashMap<>();
        items.put(phone2.getId(), QUANTITY_2);
        checkCost(items);
    }

    @Test
    public void removeNotAddedPhone() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;

        Phone phone1 = phoneList.get(0);
        Phone phone2 = phoneList.get(1);

        cartService.addPhone(phone1.getId(), QUANTITY_1);

        cartService.remove(phone2.getId());

        Cart cart = cartService.getCart();

        Assert.assertEquals(1, cart.getItems().size());
        Assert.assertEquals(QUANTITY_1, cart.getItems().get(phone1.getId()));

        verify(mockPhoneDao).get(ArgumentMatchers.eq(phone1.getId()));

        Map<Long, Long> items = new HashMap<>();
        items.put(phone1.getId(), QUANTITY_1);
        checkCost(items);
    }

    @Test
    public void checkPhonesCountInCart() throws PhoneNotFoundException {
        final Long QUANTITY_1 = 1L;
        final Long QUANTITY_2 = 2L;
        final Long QUANTITY_3 = 3L;

        Phone phone1 = phoneList.get(0);
        Phone phone2 = phoneList.get(1);
        Phone phone3 = phoneList.get(2);

        cartService.addPhone(phone1.getId(), QUANTITY_1);
        cartService.addPhone(phone2.getId(), QUANTITY_2);
        cartService.addPhone(phone3.getId(), QUANTITY_3);
        cartService.addPhone(phone1.getId(), QUANTITY_3);
        cartService.remove(phone2.getId());

        final Long TOTAL_COUNT = QUANTITY_1 + QUANTITY_3 + QUANTITY_3;

        Assert.assertEquals(TOTAL_COUNT, cartService.getPhonesCountInCart());

        InOrder inOrder = inOrder(mockPhoneDao);
        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone1.getId()));
        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone2.getId()));
        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone3.getId()));
        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone1.getId()));
        inOrder.verify(mockPhoneDao).get(ArgumentMatchers.eq(phone2.getId()));
    }

    @Test(expected = PhoneNotFoundException.class)
    public void addPhoneWithoutPrice() throws PhoneNotFoundException {
        final Long QUANTITY = 1L;

        try {
            cartService.addPhone(phoneWithoutPrice.getId(), QUANTITY);
        } finally {
            verify(mockPhoneDao).get(ArgumentMatchers.eq(phoneWithoutPrice.getId()));
        }
    }


    private void checkCost(Map<Long, Long> items) {
        BigDecimal cost = BigDecimal.ZERO;
        for (Long phoneId : items.keySet()) {
            Phone phone = phoneList.stream().filter(phone1 -> phone1.getId().equals(phoneId)).findFirst().get();
            cost = cost.add(phone.getPrice().multiply(new BigDecimal(items.get(phoneId))));
        }

        Assert.assertEquals(0, cost.compareTo(cartService.getCartCost()));
    }
}
