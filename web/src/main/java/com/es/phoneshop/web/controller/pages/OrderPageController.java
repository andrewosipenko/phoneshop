package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.services.cart.CartService;
import com.es.core.services.order.Customer;
import com.es.core.services.order.OrderService;
import com.es.core.exceptions.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private static final String REDIRECTING_ORDER_OVERVIEW_ADDRESS = "redirect:/orderOverview";
    private static final String REDIRECTING_GET_ADDRESS = "redirect:/orderOverview";
    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderPageController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping
    public String getOrder(Model model) {
        Order order;
        if (model.containsAttribute("order")) {
            order = (Order) model.asMap().get("order");
        } else {
            order = orderService.createOrder(cartService.getCart());
        }
        model.addAttribute("order", order);
        model.addAttribute("customer", new Customer());
        return "order";
    }

    @PostMapping
    public String placeOrder(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart());
        if (false) {//if future validation failed
            redirectAttributes.addAttribute("order", order);
            return REDIRECTING_GET_ADDRESS;
        }
        order.setFirstName(customer.getFirstName());
        order.setLastName(customer.getLastName());
        order.setDeliveryAddress(customer.getAddress());
        order.setContactPhoneNo(customer.getContactNumber());
        order.setAdditionalInformation(customer.getAdditionalInformation());
        order.setStatus(OrderStatus.NEW);
        orderService.placeOrder(order);
        redirectAttributes.addFlashAttribute("orderId", order.getId());
        return REDIRECTING_ORDER_OVERVIEW_ADDRESS;
    }
}
