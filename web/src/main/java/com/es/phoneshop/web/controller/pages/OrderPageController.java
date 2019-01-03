package com.es.phoneshop.web.controller.pages;

import com.es.core.form.order.OrderForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.service.cart.CartService;
import com.es.core.service.order.OrderService;
import com.es.core.exceptions.stock.OutOfStockException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) {
        Cart cart = cartService.getCart();
        OrderForm orderForm = orderService.createOrderForm(cart);
        model.addAttribute("orderForm", orderForm);
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Validated OrderForm orderForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

        }

        return "order";
    }
}
