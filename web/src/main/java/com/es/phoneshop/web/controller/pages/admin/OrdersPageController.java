package com.es.phoneshop.web.controller.pages.admin;

import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    private OrderService orderService;

    @ModelAttribute("orders")
    private List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showOrders() {
        return "orders";
    }
}
