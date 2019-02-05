package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    private final String ATTRIBUTE_LOGIN = "login";
    private final String ATTRIBUTE_ORDER = "order";
    private final String PAGE_ORDER_OVERVIEW = "orderOverview";

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/{orderKey}")
    public String showOrder(@PathVariable String orderKey, Model model, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        Order order = orderService.getOrder(orderKey);
        model.addAttribute(ATTRIBUTE_ORDER, order);
        return PAGE_ORDER_OVERVIEW;
    }
}
