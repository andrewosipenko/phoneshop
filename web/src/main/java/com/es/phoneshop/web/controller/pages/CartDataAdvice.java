package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.math.BigDecimal;

@ControllerAdvice(assignableTypes = {
        CartPageController.class,
        ProductDetailsPageController.class,
        ProductListPageController.class
})
public class CartDataAdvice {

    @Resource
    private CartService cartService;

    @ModelAttribute("cartSubTotal")
    public BigDecimal getCartSubTotal(){
        return cartService.getCartSubTotal();
    }

    @ModelAttribute("cartQuantity")
    public Long getCartQuantity(){
        return cartService.getProductsQuantity();
    }
}
