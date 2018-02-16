package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.cart.PhoneNotFoundException;
import com.es.phoneshop.web.proxy.CartServiceProxy;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:context/testContext-web.xml")
public class AjaxCartControllerTest {

    private Mockery context;

    private CartService mockCartService;

    @Resource
    private CartServiceProxy proxyCartService;

    @Resource
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        context = new JUnit4Mockery();

        mockCartService = context.mock(CartService.class);

        proxyCartService.setCartService(mockCartService);
    }

    @Test
    public void addPhone() throws Exception {
        final Long ADD_PHONE_ID = 1000L;
        final Long QUANTITY = 2L;
        final BigDecimal COST = new BigDecimal(500);
        final Long COUNT = QUANTITY;

        context.checking(new Expectations() {{
            one(mockCartService).addPhone(ADD_PHONE_ID, QUANTITY);

            one(mockCartService).getCartCost();
            will(returnValue(COST));

            one(mockCartService).getPhonesCountInCart();
            will(returnValue(COUNT));
        }});

        final String JSONContent = "{" +
                "\"phoneId\":" + ADD_PHONE_ID.toString() + "," +
                "\"quantity\":" + COUNT.toString() + "" +
                "}";

        mockMvc.perform(post("/ajaxCart").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSONContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.phonesCount").value(COUNT))
                .andExpect(jsonPath("$.cartCost").value(COST.doubleValue()));
    }

    @Test
    public void addNonexistentPhone() throws Exception {
        final Long ADD_PHONE_ID = 1000L;
        final Long QUANTITY = 2L;

        context.checking(new Expectations() {{
            one(mockCartService).addPhone(ADD_PHONE_ID, QUANTITY);
            will(throwException(new PhoneNotFoundException()));
        }});

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

        context.checking(new Expectations() {{
        }});

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

        context.checking(new Expectations());

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
