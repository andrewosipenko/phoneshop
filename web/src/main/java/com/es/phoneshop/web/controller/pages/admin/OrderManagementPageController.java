package com.es.phoneshop.web.controller.pages.admin;

import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.model.OrderStatus;
import com.es.phoneshop.core.order.service.OrderService;
import com.es.phoneshop.web.controller.throwable.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping("/admin/orders/{orderId:[0-9a-zA-Z]{10}}")
public class OrderManagementPageController {
    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String showOrderManagementPage(@PathVariable("orderId") String orderId, Model model) {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (!orderOptional.isPresent())
            throw new OrderNotFoundException();
        model.addAttribute("order", orderOptional.get());
        return "orderManagement";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderStatus(@PathVariable("orderId") String orderId, @RequestParam(name = "status") String statusString) {
        try {
            orderService.setOrderStatus(orderId, OrderStatus.valueOf(statusString));
        } catch (IllegalArgumentException ignored) {}
    }
}
