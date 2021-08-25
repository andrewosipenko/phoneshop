package com.es.phoneshop.web.controller.pages;

import com.es.core.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    public static final String ORDER_OVERVIEW_PAGE = "orderOverviewPage";
    public static final String ORDER = "order";

    @Resource
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public String getOrderOverview(@PathVariable Long orderId, Model model) {
        model.addAttribute(ORDER, orderService.getOrder(orderId));
        return ORDER_OVERVIEW_PAGE;
    }
}
