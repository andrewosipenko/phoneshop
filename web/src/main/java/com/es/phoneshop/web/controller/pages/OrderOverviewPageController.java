package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.orderDao.OrderDao;
import com.es.core.model.order.exception.NoSuchOrderException;
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

    @RequestMapping(method = RequestMethod.GET, value = "/{orderKey}")
    public String showOrder(@PathVariable String orderKey, Model model){
        model.addAttribute("order", orderDao.get(orderKey).orElseThrow(NoSuchOrderException::new));
        return "orderOverview";
    }
}
