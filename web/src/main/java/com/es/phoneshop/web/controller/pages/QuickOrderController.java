package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.form.CartItemsInfo;
import com.es.phoneshop.web.form.QuickOrderInfo;
import com.es.phoneshop.web.service.validator.ValidatorService;
import com.es.phoneshop.web.util.ParameterSetter;
import com.es.phoneshop.web.validator.QuickOrderInfoValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/quickOrder")
@PropertySource("classpath:/conf/application.properties")
public class QuickOrderController {
    private static final String CART_PARAMETER = "cart";
    private static final String ERRORS_PARAMETER = "errors";
    private static final String CART_ITEMS_INFO_PARAMETER= "cartItemsInfo";
    private static final String INPUTS_COUNT_PARAMETER = "inputsCount";
    private static final String BASE_VALIDATOR = "org.springframework";
    private static final String QUICK_ORDER_PAGE = "quickOrder";
    private static final String QUICK_ORDER_INFO_PARAMETER = "quickOrderInfo";
    private static final String ADD_SUCCESS_PARAMETER = "addSuccess";

    @Value("${quickOrder.inputsCount}")
    private int inputsCount;

    @Resource
    private CartService cartService;

    @Resource
    private ValidatorService validatorService;

    @Resource
    private QuickOrderInfoValidator quickOrderInfoValidator;

    @InitBinder
    public void setUpValidators(WebDataBinder webDataBinder) {
        if (validatorService.isAddValidator(webDataBinder, quickOrderInfoValidator, BASE_VALIDATOR)) {
            webDataBinder.addValidators(quickOrderInfoValidator);
        }
    }

    @GetMapping
    public String loadQuickOrderPage(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute(CART_PARAMETER, cart);
        model.addAttribute(CART_ITEMS_INFO_PARAMETER, new CartItemsInfo());
        model.addAttribute(INPUTS_COUNT_PARAMETER, inputsCount);
        model.addAttribute(QUICK_ORDER_INFO_PARAMETER, new QuickOrderInfo());
        ParameterSetter.setCartParameters(cart, model);
        return QUICK_ORDER_PAGE;
    }

    @PostMapping(value = "/add")
    public String addPhonesToCart(@Validated @ModelAttribute(QUICK_ORDER_INFO_PARAMETER) QuickOrderInfo quickOrderInfo,
                                BindingResult bindingResult,
                                Model model) {
        Cart cart = cartService.getCart();
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS_PARAMETER, bindingResult.getAllErrors());
            //TODO: add errorMessage to jsp
        } else {
            cartService.addCartItems(cart, quickOrderInfo.getValidData());
            model.addAttribute(ADD_SUCCESS_PARAMETER, quickOrderInfo.getValidData());
            //TODO: add successMessage to jsp
        }
        model.addAttribute(INPUTS_COUNT_PARAMETER, inputsCount);
        ParameterSetter.setCartParameters(cart, model);
        return QUICK_ORDER_PAGE;
    }
}
