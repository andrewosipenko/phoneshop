package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.web.form.CartItemsInfo;
import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.service.validator.ValidatorService;
import com.es.phoneshop.web.util.ParameterSetter;
import com.es.phoneshop.web.validator.CartValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private static final String BASE_VALIDATOR = "org.springframework";
    private static final String CART_PARAMETER = "cart";
    private static final String CART_PAGE = "cart";
    private static final String ERRORS_PARAMETER = "errors";
    private static final String CART_ITEMS_INFO_PARAMETER= "cartItemsInfo";

    @Resource
    private CartService cartService;

    @Resource
    private ValidatorService validatorService;

    @Resource
    private CartValidator cartValidator;

    @InitBinder
    public void setUpValidators(WebDataBinder webDataBinder) {
        if (validatorService.isAddValidator(webDataBinder, cartValidator, BASE_VALIDATOR)) {
            webDataBinder.addValidators(cartValidator);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute(CART_PARAMETER, cart);
        model.addAttribute(CART_ITEMS_INFO_PARAMETER, new CartItemsInfo());
        ParameterSetter.setCartParameters(cart, model);
        return CART_PAGE;
    }

    @PutMapping(value = "/update")
    public String updateCart(@Validated @ModelAttribute("cartItemsInfo") CartItemsInfo cartItemsInfo,
                             BindingResult bindingResult,
                             Model model) {
        Cart cart = cartService.getCart();

        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS_PARAMETER, bindingResult.getAllErrors());
        } else {
            Map<Long, Long> map = cartService
                    .createMapForUpdating(cartItemsInfo.getQuantity(), cart.getCartItems());
            cartService.update(cart, map);
        }

        model.addAttribute(CART_PARAMETER, cart);
        ParameterSetter.setCartParameters(cart, model);
        return CART_PAGE;
    }

    @DeleteMapping(value = "/delete/{phoneId}")
    public String deleteCartItem(@PathVariable long phoneId, Model model) {
        Cart cart = cartService.getCart();
        cartService.remove(cart, phoneId);

        model.addAttribute(CART_PARAMETER, cart);
        model.addAttribute(CART_ITEMS_INFO_PARAMETER, new CartItemsInfo());
        ParameterSetter.setCartParameters(cart, model);
        return CART_PAGE;
    }
}
