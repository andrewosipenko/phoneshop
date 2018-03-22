package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import com.es.phoneshop.web.exception.PageNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static com.es.phoneshop.web.constant.ControllerConstants.ORDER_OVERVIEW_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.ORDER_OVERVIEW_PAGE;

@Controller
@RequestMapping(ORDER_OVERVIEW_PAGE)
public class OrderOverviewPageController {

    private final static String ORDER_ATTRIBUTE = "order";

    @Resource
    private OrderService orderService;

    @GetMapping
    public String getOrderOverview(@PathVariable("id") Long id, Model model) throws PageNotFoundException {
        Order order = orderService.getOrder(id).orElseThrow(PageNotFoundException::new);
        model.addAttribute(ORDER_ATTRIBUTE, order);
        return ORDER_OVERVIEW_PAGE_NAME;
    }
}
