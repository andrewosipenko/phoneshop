package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes("order")
public class OrderPageController {

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;


    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart());
        model.addAttribute("order", order);
        return "orderPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(
            @ModelAttribute @Valid Order order,
            BindingResult bindingResult) throws OutOfStockException {

        if(bindingResult.hasErrors()){
            if(bindingResult.hasFieldErrors("orderItems")) {
                bindingResult.getFieldErrors("orderItems[*").stream()
                        .map(FieldError::getRejectedValue)
                        .forEach((item) -> order.getOrderItems().remove(item));
            }
            return "orderPage";
        } else {
            orderService.placeOrder(order);
            return "redirect:orderOverView/"+order.getUUID();
        }
    }
}
