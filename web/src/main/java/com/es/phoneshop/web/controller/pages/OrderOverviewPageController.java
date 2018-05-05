package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.service.OrderService;
import com.es.phoneshop.web.controller.throwable.OrderNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/orderOverview/{orderId:[1-9][\\d]{0,18}}")
public class OrderOverviewPageController {
    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String showOrderOverview(@PathVariable("orderId") Long orderId, Model model) {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (!orderOptional.isPresent())
            throw new OrderNotFoundException();
        model.addAttribute("order", orderOptional.get());
        return "orderOverview";
    }
}
