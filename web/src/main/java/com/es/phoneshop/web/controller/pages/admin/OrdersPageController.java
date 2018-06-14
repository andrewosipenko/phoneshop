package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderService orderService;

    @GetMapping
    public String getOrders(Model model){
        model.addAttribute("orders", orderService.getOrders());
        return "admin/orders";
    }

    @GetMapping("/{orderId}")
    public String getOrderDetails(@PathVariable Long orderId, Model model){
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "admin/orderDetails";
    }

    @PostMapping("/{orderId}")
    public String setStatus(@PathVariable Long orderId, @RequestParam OrderStatus status){
        if(status == OrderStatus.REJECTED){
            orderService.rejectOrder(orderService.getOrderById(orderId));
        }
        if(status == OrderStatus.DELIVERED){
            orderService.deliverOrder(orderService.getOrderById(orderId));
        }
        return "redirect:"+String.valueOf(orderId);
    }
}
