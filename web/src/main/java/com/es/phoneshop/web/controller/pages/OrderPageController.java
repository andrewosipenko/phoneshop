package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private final OrderService orderService;

    @Autowired
    public OrderPageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public void getOrder() throws OutOfStockException {
        orderService.createOrder(null);
    }

    @PostMapping
    public void placeOrder() throws OutOfStockException {
        orderService.placeOrder(null);
    }
}
