package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.OrderDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private OrderDao orderDao;

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public String show(@PathVariable String orderId, Model model) {
        model.addAttribute("order", orderDao.getOrder(Long.parseLong(orderId)).get());
        return "orderOverview";
    }

}
