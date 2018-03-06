package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:context/testContext-web.xml")
public class AjaxCartControllerTest extends AbstractTest {

    @Resource
    private CartService mockCartService;

    @Resource
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private Cart returnedCart;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        returnedCart = new Cart();
        when(mockCartService.getCart()).thenReturn(returnedCart);
    }

    @After
    public void resetPhoneMockDao() {
        reset(mockCartService);
    }

    @Test
    public void addPhone() throws Exception {
        final Long ADD_PHONE_ID = 1000L;
        final Long QUANTITY = 2L;
        final BigDecimal COST = new BigDecimal(500);
        final Long COUNT = QUANTITY;

        Phone phone = createPhone(ADD_PHONE_ID, 1);

        returnedCart.setCost(COST);
        returnedCart.getItems().add(new CartItem(phone, QUANTITY));

        final String JSONContent = "{" +
                "\"phoneId\":" + ADD_PHONE_ID.toString() + "," +
                "\"quantity\":" + COUNT.toString() + "" +
                "}";

        mockMvc.perform(post("/ajaxCart").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSONContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.phoneCount").value(COUNT))
                .andExpect(jsonPath("$.cartCost").value(COST.doubleValue()));
    }

    @Test
    public void addNonexistentPhone() throws Exception {
        final Long ADD_PHONE_ID = 1000L;
        final Long QUANTITY = 2L;

        doThrow(PhoneNotFoundException.class).when(mockCartService).addPhone(ADD_PHONE_ID, QUANTITY);

        final String JSONContent = "{" +
                "\"phoneId\":" + ADD_PHONE_ID.toString() + "," +
                "\"quantity\":" + QUANTITY.toString() + "" +
                "}";

        mockMvc.perform(post("/ajaxCart").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSONContent))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.errorMessage").value("Wrong format"));
    }

    @Test
    public void addPhoneWithIncorrectId() throws Exception {
        final Long ADD_PHONE_ID = 0L;
        final Long QUANTITY = 2L;

        final String JSONContent = "{" +
                "\"phoneId\":" + ADD_PHONE_ID.toString() + "," +
                "\"quantity\":" + QUANTITY.toString() + "" +
                "}";

        sendRequestAndCheckErrorMessage(JSONContent);
    }

    @Test
    public void addPhoneWithIncorrectQuantity() throws Exception {
        final Long ADD_PHONE_ID = 0L;
        final Long QUANTITY = -1L;

        final String JSONContent = "{" +
                "\"phoneId\":" + ADD_PHONE_ID.toString() + "," +
                "\"quantity\":" + QUANTITY.toString() + "" +
                "}";

        sendRequestAndCheckErrorMessage(JSONContent);
    }

    @Test
    public void addPhoneWithQuantityIsString() throws Exception {
        final Long ADD_PHONE_ID = 0L;

        final String JSONContent = "{" +
                "\"phoneId\":" + ADD_PHONE_ID.toString() + "," +
                "\"quantity\":\"test\"" +
                "}";

        sendRequestAndCheckErrorMessage(JSONContent);
    }

    private void sendRequestAndCheckErrorMessage(String JSONContent) throws Exception {
        mockMvc.perform(post("/ajaxCart").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSONContent))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.errorMessage").value("Wrong format"));
    }

}
