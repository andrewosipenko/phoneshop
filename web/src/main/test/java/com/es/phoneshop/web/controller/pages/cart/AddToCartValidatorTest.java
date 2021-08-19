package com.es.phoneshop.web.controller.pages.cart;

import com.es.core.service.phone.PhoneStockService;
import com.es.phoneshop.web.conf.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.Errors;

import javax.annotation.Resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class,
loader = AnnotationConfigContextLoader.class)
class AddToCartValidatorTest {

    @Resource
    private AddToCartValidator addToCartValidatorTest;

    @Resource
    @Mock
    private PhoneStockService phoneStockService;

    private static final Long PHONE_ID = 1002L;
    private static final Long QUANTITY = 2L;

    private static final AddProductToCartForm CART_FORM = new AddProductToCartForm(1002L, "2");
    private static final AddProductToCartForm CART_FORM_NULL = new AddProductToCartForm(1002L, null);
    private static final AddProductToCartForm CART_FORM_LESS_ZERO = new AddProductToCartForm(1002L, "-3");




    @Test
    void validateWithQuantityNull() {
        Errors errors = mock(Errors.class);
        addToCartValidatorTest.validate(CART_FORM_NULL, errors);
        verify(errors, atLeastOnce()).reject("quantity.invalid", "The value must must be a number");
    }

    @Test
    void validateWithQuantityLssZero() {
        Errors errors = mock(Errors.class);
        addToCartValidatorTest.validate(CART_FORM_LESS_ZERO, errors);
        verify(errors, atLeastOnce()).reject("quantity.negative", "The value mus be greater than 0");
    }


    @Test
    void validateWithNotEnoughStock() {
        Errors errors = mock(Errors.class);
        addToCartValidatorTest.validate(CART_FORM, errors);
        when(phoneStockService.hasEnoughStock(any(), any())).thenReturn(true);
        verify(errors, atLeastOnce()).reject("quantity.not.available", "Not enough stock for this product");
    }
}