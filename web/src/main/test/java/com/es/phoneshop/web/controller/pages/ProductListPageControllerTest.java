package com.es.phoneshop.web.controller.pages;

import com.es.core.service.cart.CartService;
import com.es.core.service.phone.ProductPageService;
import com.es.phoneshop.web.conf.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class,
loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration()
class ProductListPageControllerTest {

    @Resource
    private CartService cartService;

    @Resource
    private ProductPageService productPageService;

    @Resource
    private WebApplicationContext wac;

    private MockMvc mockMvc;



    @Test
    void showProductList() {

    }


}