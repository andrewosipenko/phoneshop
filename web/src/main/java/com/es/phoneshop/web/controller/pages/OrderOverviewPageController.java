package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.order.OrderDao;
import com.es.core.model.order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    private static final String ORDER_OVERVIEW_PAGE = "orderOverview";
    private static final String ORDER_PARAMETER = "order";

    @Resource
    private OrderDao orderDao;

    @RequestMapping(value = "/{secureId}", method = RequestMethod.GET)
    public String getOrder(@PathVariable String secureId, Model model) {
        Order order = orderDao.findOrderBySecureId(secureId);
        model.addAttribute(ORDER_PARAMETER, order);
        return ORDER_OVERVIEW_PAGE;
    }
}
