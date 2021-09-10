package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import com.es.core.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    private static final String ORDERS_ATTRIBUTE = "orders";

    private static final String ORDER_ATTRIBUTE = "order";

    @Resource
    private OrderService orderService;

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute(ORDERS_ATTRIBUTE, orderService.getOrders());
        return "adminOrdersPage";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        model.addAttribute(ORDER_ATTRIBUTE, orderService.getOrder(id));
        return "adminOrderPage";
    }

    @PostMapping("/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam OrderStatus orderStatus,
                               Model model) {
        orderService.updateOrderStatus(id, orderStatus);
        return getOrder(id, model);
    }
}
