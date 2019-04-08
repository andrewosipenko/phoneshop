package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.service.order.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    private static final String ORDERS_PAGE = "/admin/orders";
    private static final String ORDERS_PARAMETER = "orders";
    private static final String USERNAME_PARAMETER = "username";
    private static final String STATUS_PARAMETER = "status";

    @Resource
    private OrderService orderService;

    @RequestMapping
    public String getAdminOrdersPage(Model model) {
        model.addAttribute(USERNAME_PARAMETER, SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute(ORDERS_PARAMETER, orderService.findAll());
        return ORDERS_PAGE;
    }
}
