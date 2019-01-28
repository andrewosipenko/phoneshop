package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.form.order.OrderForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.service.cart.CartService;
import com.es.core.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private final String ERROR_ATTRIBUTE = "errorMessage";
    private final String ERROR_MESSAGE = "Some phones are out of the stock." +
            " So they were removed from your cart";
    private final String ORDER_ATTRIBUTE = "orderForm";

    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) {
        if (cartService.removePhonesOutOfTheStock()) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_MESSAGE);
        }
        Cart cart = cartService.getCart();
        OrderForm orderForm = orderService.createOrderForm(cart);
        model.addAttribute(ORDER_ATTRIBUTE, orderForm);
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Validated OrderForm orderForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            orderService.updateOrderForm(orderForm, cartService.getCart());
            return "order";
        }
        try {
            Order order = orderService.createOrder(orderForm, cartService.getCart());
            orderService.placeOrder(order);
            String orderKey = orderService.getOrderKey(order.getId());
            return "redirect:/orderOverview/" + orderKey;
        } catch (OutOfStockException ex) {
            cartService.removePhonesOutOfTheStock();
            orderService.updateOrderForm(orderForm, cartService.getCart());
            model.addAttribute(ERROR_ATTRIBUTE, ex.getMessage());
            return "order";
        }
    }
}
