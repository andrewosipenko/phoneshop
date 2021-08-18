package com.es.phoneshop.web.conf;

import com.es.core.service.phone.PhoneStockService;
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
}
