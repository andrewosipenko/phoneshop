package com.es.phoneshop.web.conf;

import com.es.core.service.cart.CartService;
import com.es.core.service.cart.HttpSessionCartService;
import com.es.core.service.phone.PhoneStockService;
import com.es.core.service.phone.ProductPageService;
import com.es.core.service.phone.impl.ProductPageServiceImpl;
import com.es.phoneshop.web.controller.pages.ProductListPageController;
import com.es.phoneshop.web.controller.pages.cart.AddToCartValidator;
import com.es.phoneshop.web.controller.pages.cart.UpdateCartValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public UpdateCartValidator updateCartValidator() {
        return new UpdateCartValidator();
    }

    @Bean
    public PhoneStockService phoneStockService() {
        return mock(PhoneStockService.class);
    }

    @Bean
    public AddToCartValidator addToCartValidator() {
        return new AddToCartValidator();
    }

    @Bean
    public ProductListPageController productListPageController() {
        return new ProductListPageController();
    }

    @Bean
    public ProductPageService productPageService() {
        return mock(ProductPageService.class);
    }

    @Bean
    public CartService cartService() {
        return mock(CartService.class);
    }
}
