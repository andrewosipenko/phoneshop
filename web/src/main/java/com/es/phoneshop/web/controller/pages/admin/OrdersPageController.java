package com.es.phoneshop.web.controller.pages.admin;

import com.es.phoneshop.core.model.order.Order;
import com.es.phoneshop.core.model.order.OrderDao;
import com.es.phoneshop.web.controller.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    private OrderDao orderDao;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrders(Model model){
        List<Order> ordersList = orderDao.findAll();

        model.addAttribute("ordersList", ordersList);

        return "admin/orders";
    }

    @RequestMapping(value="/{orderId}", method = RequestMethod.POST)
    public String setOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam("setStatus") Integer status) {
        orderDao.changeStatus(orderId, status);
        return "redirect:/admin/orders/"+orderId;
    }

    @RequestMapping(value="/{orderId}")
    public String getOrderInfo(@PathVariable("orderId") Long orderId, Model model) {
        Optional<Order> order = orderDao.findById(orderId);

        if(order.isPresent()) {
            model.addAttribute("order", order.get());
            return "admin/orderInfo";
        } else {
            throw new OrderNotFoundException();
        }
    }
}
