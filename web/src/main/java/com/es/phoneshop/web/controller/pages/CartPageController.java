package com.es.phoneshop.web.controller.pages;

import com.es.core.form.CartItemsInfo;
import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.util.ParameterSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private final String CART_PARAMETER = "cart";
    private final String CART_PAGE = "cart";
    private final String ERRORS_PARAMETER = "errors";
    private final String CART_ITEMS_INFO_PARAMETER= "cartItemsInfo";

    @Resource
    private CartService cartService;

    @Autowired
    @Qualifier("cartValidator")
    private Validator validator;

    @InitBinder
    public void setUpValidators(WebDataBinder webDataBinder) {
        if (webDataBinder.getTarget() != null) {
            if (validator.supports(webDataBinder.getTarget().getClass())
                    && !validator.getClass().getName().contains("org.springframework"))
                webDataBinder.addValidators(validator);
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
            System.out.println(bindingResult.getAllErrors());
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
