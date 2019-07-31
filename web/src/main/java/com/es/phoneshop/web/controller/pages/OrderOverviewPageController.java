package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.ProductDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @Resource
    private OrderDao orderDao;

    @Resource
    private CartService cartService;

    @Resource
    private ProductDao productDao;

    @Value("${delivery.price}")
    private double deliveryPrice;

    @RequestMapping(method = RequestMethod.GET)
    public String orderOverview(@RequestParam Long orderId, Model model) {
        Order order = orderDao.loadOrderById(orderId);
        model.addAttribute("order", order);
        return "orderOverview";
    }

}
