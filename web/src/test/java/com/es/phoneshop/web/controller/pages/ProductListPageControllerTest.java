package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.services.cart.CartService;
import com.es.core.services.cart.TotalPriceService;
import com.es.core.services.phone.PhoneService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ProductListPageControllerTest {
    private static final String KEYWORD = "keyword";
    private static final int LIMIT = 10;
    private static final int FIRST_PAGE_NUMBER = 1;
    private static List<Phone> phones = new ArrayList<>();
    private CartService cartService = mock(CartService.class);
    private PhoneService phoneService = mock(PhoneService.class);
    private TotalPriceService totalPriceService = mock(TotalPriceService.class);
    private ProductListPageController controller = new ProductListPageController(phoneService, cartService, totalPriceService);

    @BeforeClass
    public static void init() {
        for (int i = 0; i < 50; i++) {
            phones.add(new Phone());
        }
    }

    @Test
    public void shouldReturnPhonesByKeyword() {
        when(phoneService.getPhonesByKeyword(KEYWORD)).thenReturn(phones);

        assertArrayEquals(phones.toArray(), controller.findPhonesBySearch(KEYWORD).toArray());
    }

    @Test
    public void shouldFindLimitAmountOfPhonesForFirstPage() {
        when(phoneService.getTotalAmountOfPhonesWithPositiveStock()).thenReturn((long) phones.size());
        when(phoneService.getPhonesWithPositiveStock(FIRST_PAGE_NUMBER - 1, LIMIT)).thenReturn(phones.subList(FIRST_PAGE_NUMBER - 1, LIMIT));

        List<Phone> actualPhoneList = controller.findPhonesForCurrentPage(FIRST_PAGE_NUMBER);

        assertEquals(LIMIT, actualPhoneList.size());
        assertArrayEquals(phones.subList(FIRST_PAGE_NUMBER-1, LIMIT).toArray(), actualPhoneList.toArray());
    }
}