package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.dao.orderDao.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.order.exception.NoSuchOrderException;
import com.es.core.service.order.OrderStatusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderStatusService orderStatusService;

    @GetMapping
    public String showOrders(Model model){
        model.addAttribute("orders", orderDao.getAll());
        return "adminOrders";
    }

    @GetMapping(value = "/{orderId}")
    public String showOrder(@PathVariable Long orderId,  Model model){
        Order order = orderDao.get(orderId).orElseThrow(NoSuchOrderException::new);
        model.addAttribute("order", order);
        model.addAttribute("orderStatusNames", orderStatusService.getOrderStatusNamesExcept(order.getStatus()));
        return "adminOrder";
    }

    @PostMapping(value = "/{orderId}/setNewOrderStatus")
    public String setNewOrderStatus(@PathVariable Long orderId,
                                    @RequestParam("newOrderStatus") OrderStatus newOrderStatus,
                                    RedirectAttributes redirectAttributes){
        orderDao.updateOrderStatus(orderId, newOrderStatus);
        redirectAttributes.addFlashAttribute("updateOrderStatusMessage",
                "Order number " + orderId + " was updated with status " + newOrderStatus);
        return "redirect:/admin/orders";
    }

}
