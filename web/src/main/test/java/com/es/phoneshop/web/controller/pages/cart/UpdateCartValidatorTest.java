package com.es.phoneshop.web.controller.pages.cart;

import com.es.core.service.phone.PhoneStockService;
import com.es.phoneshop.web.conf.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
class UpdateCartValidatorTest {

    @Resource
    private UpdateCartValidator updateCartValidator;

    @Resource
    private PhoneStockService phoneStockService;

    private static final String QUANTITY_TEST = "2";
    private static final String PHONE_ID = "1002";
    private static CartItemsForUpdate cartItemForUpdateWithQuantityNull = new CartItemsForUpdate();
    private static CartItemsForUpdate  cartItemForUpdateWithQuantityLessZero = new CartItemsForUpdate();
    private static CartItemsForUpdate  cartItemForUpdateWithQuantity = new CartItemsForUpdate();

    static {
        Map<Long, String> mapWithValueNull = new HashMap<>();
        Map<Long, String> mapWithValueLessZero = new HashMap<>();
        Map<Long, String> mapWithValueQuantity = new HashMap<>();
        mapWithValueNull.put(1002L, null);
        mapWithValueLessZero.put(1002L, "-3");
        mapWithValueQuantity.put(1002L, "2");

        cartItemForUpdateWithQuantityNull.setCartItems(mapWithValueNull);
        cartItemForUpdateWithQuantityLessZero.setCartItems(mapWithValueLessZero);
        cartItemForUpdateWithQuantity.setCartItems(mapWithValueQuantity);
    }

    private static final CartItemsForUpdate cartItemsForUpdate = mock(CartItemsForUpdate.class);


    @Test
    void supports() {

    }

    @Test
    void validateWithQuantityNull() {
        Errors errors = mock(Errors.class);
        updateCartValidator.validate(cartItemForUpdateWithQuantityNull, errors);
        verify(errors, atLeastOnce()).rejectValue(any(), any(), any());
    }

    @Test
    void validateWithQuantityLessZero() {
        Errors errors = mock(Errors.class);
        updateCartValidator.validate(cartItemForUpdateWithQuantityLessZero, errors);
        verify(errors, atLeastOnce()).rejectValue(any(), any(), any());
    }

    /*@Test
    void validateWithNormalQuantity() {
        Errors errors = mock(Errors.class);
        updateCartValidator.validate(cartItemForUpdateWithQuantity, errors);
        when(phoneStockService.hasEnoughStock(any(), any())).thenReturn(true);
        verify(errors, never()).rejectValue(any(), any(), any());
    }*/

    @Test
    void validateHasEnoughStock() {
        Errors errors = mock(Errors.class);
        updateCartValidator.validate(cartItemForUpdateWithQuantity, errors);
        when(phoneStockService.hasEnoughStock(any(), any())).thenReturn(false);
        verify(errors, atLeastOnce()).rejectValue(any(), any(), any());
    }

}