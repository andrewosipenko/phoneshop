package com.es.core.service.cart;


import com.es.core.model.DAO.phone.PhoneDao;
import com.es.core.model.entity.cart.Cart;
import com.es.core.model.entity.phone.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HttpSessionCartServiceTest {

    static final Long FIRST_PHONE_ID = 1L;
    static final Long SECOND_PHONE_ID = 2L;
    static final Long THIRD_PHONE_ID = 3L;
    static final BigDecimal FIRST_PHONE_PRICE = new BigDecimal(100);
    static final BigDecimal SECOND_PHONE_PRICE = new BigDecimal(200);
    static final BigDecimal THIRD_PHONE_PRICE = new BigDecimal(300);
    @InjectMocks
    HttpSessionCartService httpSessionCartService;
    @Mock
    PhoneDao phoneDao;
    Cart cart;
    Phone firstTestPhone;
    Phone secondTestPhone;
    Phone thirdTestPhone;

    @BeforeEach
    void setup() {
        cart = new Cart();
        setupTestPhones();
    }

    void setupTestPhones() {
        firstTestPhone = new Phone();
        firstTestPhone.setId(FIRST_PHONE_ID);
        firstTestPhone.setPrice(FIRST_PHONE_PRICE);

        secondTestPhone = new Phone();
        secondTestPhone.setId(SECOND_PHONE_ID);
        secondTestPhone.setPrice(SECOND_PHONE_PRICE);

        thirdTestPhone = new Phone();
        thirdTestPhone.setId(THIRD_PHONE_ID);
        thirdTestPhone.setPrice(THIRD_PHONE_PRICE);
    }


    @Test
    void addNewPhonesToCartQuantityTest() {
        when(phoneDao.get(FIRST_PHONE_ID)).thenReturn(Optional.ofNullable(firstTestPhone));
        when(phoneDao.get(SECOND_PHONE_ID)).thenReturn(Optional.ofNullable(secondTestPhone));
        when(phoneDao.get(THIRD_PHONE_ID)).thenReturn(Optional.ofNullable(thirdTestPhone));

        Long firstPhoneQuantity = 3L;
        Long secondPhoneQuantity = 2L;
        Long thirdPhoneQuantity = 1L;

        httpSessionCartService.addPhone(cart, FIRST_PHONE_ID, firstPhoneQuantity);
        httpSessionCartService.addPhone(cart, SECOND_PHONE_ID, secondPhoneQuantity);
        httpSessionCartService.addPhone(cart, THIRD_PHONE_ID, thirdPhoneQuantity);

        assertEquals(cart.getItems().size(), 3);
    }

    @Test
    void addExistingPhoneToCartQuantityTest() {
        when(phoneDao.get(FIRST_PHONE_ID)).thenReturn(Optional.ofNullable(firstTestPhone));
        when(phoneDao.get(THIRD_PHONE_ID)).thenReturn(Optional.ofNullable(thirdTestPhone));

        Long firstPhoneQuantity = 3L;
        Long thirdPhoneQuantity = 1L;

        httpSessionCartService.addPhone(cart, FIRST_PHONE_ID, firstPhoneQuantity);
        httpSessionCartService.addPhone(cart, FIRST_PHONE_ID, firstPhoneQuantity);

        httpSessionCartService.addPhone(cart, THIRD_PHONE_ID, thirdPhoneQuantity);
        httpSessionCartService.addPhone(cart, THIRD_PHONE_ID, thirdPhoneQuantity);
        httpSessionCartService.addPhone(cart, THIRD_PHONE_ID, thirdPhoneQuantity);
        httpSessionCartService.addPhone(cart, THIRD_PHONE_ID, thirdPhoneQuantity);

        assertEquals(cart.getItems().size(), 2);
    }


    @Test
    void addPhonesTotalQuantityTest() {
        when(phoneDao.get(FIRST_PHONE_ID)).thenReturn(Optional.ofNullable(firstTestPhone));
        when(phoneDao.get(SECOND_PHONE_ID)).thenReturn(Optional.ofNullable(secondTestPhone));
        when(phoneDao.get(THIRD_PHONE_ID)).thenReturn(Optional.ofNullable(thirdTestPhone));

        Long firstPhoneQuantity = 3L;
        Long secondPhoneQuantity = 2L;
        Long thirdPhoneQuantity = 1L;

        long numberOfFirstPhoneAdditions = 2;
        long numberOfSecondPhoneAdditions = 3;
        long numberOfThirdPhoneAdditions = 1;

        for (int i = 0; i < numberOfFirstPhoneAdditions; i++) {
            httpSessionCartService.addPhone(cart, FIRST_PHONE_ID, firstPhoneQuantity);
        }

        for (int i = 0; i < numberOfSecondPhoneAdditions; i++) {
            httpSessionCartService.addPhone(cart, SECOND_PHONE_ID, secondPhoneQuantity);
        }

        for (int i = 0; i < numberOfThirdPhoneAdditions; i++) {
            httpSessionCartService.addPhone(cart, THIRD_PHONE_ID, thirdPhoneQuantity);
        }

        long expectedTotalQuantity = firstPhoneQuantity * numberOfFirstPhoneAdditions
                + secondPhoneQuantity * numberOfSecondPhoneAdditions
                + thirdPhoneQuantity * numberOfThirdPhoneAdditions;

        assertEquals(cart.getTotalQuantity(), expectedTotalQuantity);
    }

    @Test
    void addPhonesTotalCostTest() {
        when(phoneDao.get(FIRST_PHONE_ID)).thenReturn(Optional.ofNullable(firstTestPhone));
        when(phoneDao.get(SECOND_PHONE_ID)).thenReturn(Optional.ofNullable(secondTestPhone));
        when(phoneDao.get(THIRD_PHONE_ID)).thenReturn(Optional.ofNullable(thirdTestPhone));

        Long firstPhoneQuantity = 3L;
        Long secondPhoneQuantity = 2L;
        Long thirdPhoneQuantity = 1L;

        long numberOfFirstPhoneAdditions = 2;
        long numberOfSecondPhoneAdditions = 3;
        long numberOfThirdPhoneAdditions = 1;

        for (int i = 0; i < numberOfFirstPhoneAdditions; i++) {
            httpSessionCartService.addPhone(cart, FIRST_PHONE_ID, firstPhoneQuantity);
        }

        for (int i = 0; i < numberOfSecondPhoneAdditions; i++) {
            httpSessionCartService.addPhone(cart, SECOND_PHONE_ID, secondPhoneQuantity);
        }

        for (int i = 0; i < numberOfThirdPhoneAdditions; i++) {
            httpSessionCartService.addPhone(cart, THIRD_PHONE_ID, thirdPhoneQuantity);
        }

        BigDecimal expectedTotalCost = BigDecimal
                .valueOf(firstPhoneQuantity * numberOfFirstPhoneAdditions).multiply(FIRST_PHONE_PRICE)
                .add(BigDecimal.valueOf(secondPhoneQuantity * numberOfSecondPhoneAdditions).multiply(SECOND_PHONE_PRICE))
                .add(BigDecimal.valueOf(thirdPhoneQuantity * numberOfThirdPhoneAdditions).multiply(THIRD_PHONE_PRICE));

        assertEquals(cart.getTotalCost(), expectedTotalCost);
    }
}