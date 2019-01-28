package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/{orderKey}")
    public String showOrder(@PathVariable String orderKey, Model model){
        Long orderId = Long.valueOf(orderKey);
        Order order = orderService.getOrder(orderId);
        model.addAttribute("order", order);
        return "orderOverview";
    }
}
