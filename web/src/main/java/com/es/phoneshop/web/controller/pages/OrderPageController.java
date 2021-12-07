package com.es.phoneshop.web.controller.pages;

import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    public static final String ORDER = "order";
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showOrderPage(){
        return ORDER;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void getOrder() throws OutOfStockException {
        orderService.createOrder(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void placeOrder() throws OutOfStockException {
        orderService.placeOrder(null);
    }
}
