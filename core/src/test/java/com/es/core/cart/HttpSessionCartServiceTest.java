package com.es.core.cart;

import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:applicationTestContext-core.xml")
public class HttpSessionCartServiceTest {
    @Mock
    private Cart mockCart;

    @Mock
    private PhoneDao mockPhoneDao;

    @InjectMocks
    private CartService cartService = new HttpSessionCartService();

    private List<CartItem> cartItemList;

    private List<Phone> phoneList;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        cartItemList = new ArrayList<>();
        when(mockCart.getItems()).thenReturn(cartItemList);
        initPhoneList();
        initPhoneDao();
    }

    private void initPhoneList() {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        Phone phone3 = new Phone();
        phone.setId(1000L);
        phone1.setId(1001L);
        phone2.setId(1002L);
        phone3.setId(1003L);
        phone.setPrice(BigDecimal.valueOf(100));
        phone1.setPrice(BigDecimal.valueOf(200));
        phone2.setPrice(BigDecimal.valueOf(300));
        phone3.setPrice(BigDecimal.valueOf(400));

        phoneList = Arrays.asList(phone, phone1, phone2, phone3);
    }

    private void initPhoneDao() {
        for( Phone phone : phoneList) {
            when(mockPhoneDao.get(phone.getId())).thenReturn(Optional.of(phone));
        }
    }

    @Test
    public void addExistingPhoneTest() throws PhoneNotFoundException {
        cartService.addPhone(1000L, 2L);
        assertEquals(1, cartItemList.size());
        verify(mockCart).setCost(BigDecimal.valueOf(200));
    }

    @Test(expected = PhoneNotFoundException.class)
    public void addUnExistingPhoneTest() throws PhoneNotFoundException {
         cartService.addPhone(999L, 2L);
    }

    @Test
    public void addSeveralPhonesTest() throws PhoneNotFoundException {
        Long quantity = 1L;
        BigDecimal cost = addAllPhonesAndReturnCost(quantity);
        verify(mockCart).setCost(cost);
        assertEquals(phoneList.size(), mockCart.getItems().size());
        Phone phone = mockPhoneDao.get(1000L).get();
        Long otherQuantity = 3L;
        cartService.addPhone(phone.getId(), otherQuantity);
        cost = cost.add(phone.getPrice().multiply(BigDecimal.valueOf(otherQuantity)));
        verify(mockCart).setCost(cost);
        assertEquals(phoneList.size(), mockCart.getItems().size());
    }

    @Test
    public void removeExistingInCartPhoneTest() throws PhoneNotFoundException, PhoneInCartNotFoundException {
        Long quantity = 3L;
        BigDecimal cost = addAllPhonesAndReturnCost(quantity);
        Phone phone = mockPhoneDao.get(1003L).get();
        cartService.remove(phone.getId());
        cost = cost.subtract(phone.getPrice().multiply(BigDecimal.valueOf(quantity)));
        verify(mockCart, atLeastOnce()).setCost(cost);
        assertEquals(phoneList.size() - 1, mockCart.getItems().size());
    }

    @Test(expected = PhoneInCartNotFoundException.class)
    public void removeUnExistingPhoneTest() throws PhoneInCartNotFoundException {
        cartService.remove(999L);
    }

    @Test
    public void updateWithExistingItemsTest() throws PhoneNotFoundException, PhoneInCartNotFoundException {
        Long quantity = 1L;
        Map<Long, Long> itemsMap = new HashMap<>();
        itemsMap.put(1000L, 1L);
        itemsMap.put(1001L, 3L);
        itemsMap.put(1002L, 2L);
        itemsMap.put(1003L, 3L);
        addAllPhonesAndReturnCost(quantity);
        BigDecimal updatedCost = BigDecimal.ZERO;
        for(Map.Entry<Long, Long> item : itemsMap.entrySet()) {
            updatedCost = updatedCost.add(mockPhoneDao.get(item.getKey()).get()
                    .getPrice().multiply(BigDecimal.valueOf(item.getValue())));
        }
        cartService.update(itemsMap);
        verify(mockCart).setCost(updatedCost);
        assertEquals(itemsMap.size(), mockCart.getItems().size());
    }

    @Test(expected = PhoneInCartNotFoundException.class)
    public void updateWithUnExistingItemsTest() throws PhoneInCartNotFoundException {
        Map<Long, Long> itemsMap = new HashMap<>();
        itemsMap.put(1000L, 1L);
        itemsMap.put(999L, 3L);
        cartService.update(itemsMap);
    }

    private BigDecimal addAllPhonesAndReturnCost(Long quantity) throws PhoneNotFoundException {
        BigDecimal cost = phoneList.stream()
                .map(Phone::getPrice)
                .reduce(BigDecimal::add).get()
                .multiply(BigDecimal.valueOf(quantity));

        for(Phone phone : phoneList) {
            cartService.addPhone(phone.getId(), quantity);
        }
        return cost;
    }
}
