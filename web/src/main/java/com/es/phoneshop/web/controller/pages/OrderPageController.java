package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.service.cart.CartService;
import com.es.core.service.order.OrderService;
import com.es.phoneshop.web.controller.pages.order.OrderForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    @Resource
    private CartService cartService;

    @Resource
    private OrderService orderService;

    @Resource
    @Qualifier("stockValidator")
    private Validator stockValidator;

    @GetMapping
    public String getOrder(Model model) throws OutOfStockException {
        model.addAttribute("orderForm", new OrderForm());
        model.addAttribute("cart", cartService.getCart());
        return "orderPage";
    }

    @PostMapping
    public String placeOrder(@ModelAttribute @Valid OrderForm orderForm,
                             BindingResult bindingResult,
                             Model model) throws OutOfStockException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cart", cartService.getCart());
            return "orderPage";
        }

        stockValidator.validate(cartService.getCart(), bindingResult);
        if (bindingResult.hasErrors()) {
            throw new OutOfStockException();
        }

        Order order = orderService.createOrder(cartService.getCart());
        order.setFirstName(orderForm.getFirstName());
        order.setLastName(orderForm.getLastName());
        order.setDeliveryAddress(orderForm.getAddress());
        order.setContactPhoneNo(orderForm.getPhone());
        order.setAdditionalInfo(orderForm.getAdditionalInfo());
        Long id = orderService.placeOrder(order);
        return "redirect:orderOverview/" + id;
    }
}
