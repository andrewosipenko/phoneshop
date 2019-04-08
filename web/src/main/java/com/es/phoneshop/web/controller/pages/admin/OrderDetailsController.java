package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "admin/orders/{orderId}")
public class OrderDetailsController {
    private static final String ADMIN_ORDER_PAGE = "admin/adminOrder";
    private static final String ORDER_PARAMETER = "order";
    private static final String USERNAME_PARAMETER = "username";

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrderDetailsPage(@PathVariable Long orderId, Model model) {
        model.addAttribute(ORDER_PARAMETER, orderService.findOrderById(orderId));
        model.addAttribute(USERNAME_PARAMETER, SecurityContextHolder.getContext().getAuthentication().getName());
        return ADMIN_ORDER_PAGE;
    }

    @PutMapping
    public String updateStatus(@PathVariable Long orderId,
                             @RequestParam String status,
                             Model model) {
        Order order = orderService.findOrderById(orderId);
        orderService.placeOrder(order, status.toUpperCase());
        model.addAttribute(ORDER_PARAMETER, order);
        model.addAttribute(USERNAME_PARAMETER, SecurityContextHolder.getContext().getAuthentication().getName());
        return ADMIN_ORDER_PAGE;
    }
}
