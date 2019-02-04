package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.form.order.OrderForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.service.cart.CartService;
import com.es.core.service.order.OrderService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private final String ATTRIBUTE_LOGIN = "login";
    private final String ATTRIBUTE_ORDER_FORM = "orderForm";
    private final String ATTRIBUTE_ERROR = "errorMessage";
    private final String PAGE_ORDER = "order";
    private final String REDIRECT_PAGE_OVERVIEEW = "redirect:/orderOverview/";
    private final String MESSAGE_ERROR = "Some phones are out of the stock." +
            " So they were removed from your cart";

    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        if (cartService.removePhonesOutOfTheStock()) {
            model.addAttribute(ATTRIBUTE_ERROR, MESSAGE_ERROR);
        }
        Cart cart = cartService.getCart();
        OrderForm orderForm = orderService.createOrderForm(cart);
        model.addAttribute(ATTRIBUTE_ORDER_FORM, orderForm);
        return PAGE_ORDER;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid OrderForm orderForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            orderService.updateOrderForm(orderForm, cartService.getCart());
            return PAGE_ORDER;
        }
        try {
            Order order = orderService.createOrder(orderForm, cartService.getCart());
            orderService.placeOrder(order);
            String orderKey = orderService.getOrderKey(order.getId());
            return REDIRECT_PAGE_OVERVIEEW + orderKey;
        } catch (OutOfStockException ex) {
            cartService.removePhonesOutOfTheStock();
            orderService.updateOrderForm(orderForm, cartService.getCart());
            model.addAttribute(ATTRIBUTE_ERROR, ex.getMessage());
            return PAGE_ORDER;
        }
    }
}
