package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.order.OrderService;
import com.es.core.service.order.OrderStatusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderStatusService orderStatusService;

    @RequestMapping(method = RequestMethod.GET)
    public String showOrders(Model model){
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "adminOrders";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    public String showOrder(@PathVariable Long orderId, Model model){
        Order order = orderService.getOrder(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderStatusName", orderStatusService.getOrderStatusNamesExcept(order.getStatus()));
        return "adminOrder";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{orderId}/setNewOrderStatus")
    public String setNewOrderStats(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus, RedirectAttributes redirectAttributes){
        orderService.updateOrderStatus(orderId, orderStatus);
        redirectAttributes.addFlashAttribute("orderStatusMessage",
                "Order number " + orderId + " was updated to status " + orderStatus);
        return "redirect:/admin/orders";
    }

}
