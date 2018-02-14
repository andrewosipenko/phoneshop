package com.es.core.cart;

import com.es.core.AbstractTest;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration("classpath:context/testContext.xml")
public class HttpSessionCartServiceTest extends AbstractTest {

    @Resource
    private CartService cartService;

    @Resource
    private HttpSession httpSession;

    private List<Phone> phoneList;

    @Before
    public void init() {
        phoneList = addNewPhones(5);
        httpSession.removeAttribute("cart");
        httpSession.removeAttribute("cartCost");
    }


    @Test
    public void addToCartPhone() throws PhoneNotFoundException {
        final Long QUANTITY = 1L;

        Phone phone = phoneList.get(0);

        cartService.addPhone(phone.getId(), QUANTITY);

        Cart cart = cartService.getCart();

        Assert.assertEquals(1, cart.getItems().size());
        Assert.assertEquals(QUANTITY, cart.getItems().get(phone.getId()));

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

        Map<Long, Long> items = new HashMap<>();
        items.put(phone1.getId(), QUANTITY_1);
        items.put(phone2.getId(), QUANTITY_2);
        checkCost(items);
    }

    @Test(expected = PhoneNotFoundException.class)
    public void addNonexistentPhone() throws PhoneNotFoundException {
        final Long NONEXISTENT_PHONE = -1L;
        final Long QUANTITY = 1L;

        cartService.addPhone(NONEXISTENT_PHONE, QUANTITY);
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
        cartService.update(items);
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

        Map<Long, Long> items = new HashMap<>();
        items.put(phone1.getId(), QUANTITY_1);
        checkCost(items);
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
