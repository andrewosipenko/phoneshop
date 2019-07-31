package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderService orderService;

    @RequestMapping
    public String adminPage(Model model) {
        List<Order> orderList = orderDao.loadAllOrders();
        model.addAttribute("orders", orderList);
        return "admin/orders";
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public String orderInfo(@PathVariable Long orderId, Model model) {
        Order order = orderDao.loadOrderById(orderId);
        model.addAttribute(order);
        return "admin/orderOverview";
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.POST)
    public String orderStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateStatus(orderId, status);
        return "redirect:/admin/orders";
    }
}
