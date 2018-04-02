package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private PhoneService phoneService;

    private static final String ATTRIBUTE_ORDERS = "orders";
    private static final String ATTRIBUTE_ORDER = "order";
    private static final String ATTRIBUTE_PHONES = "phones";

    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute(ATTRIBUTE_ORDERS, orderService.getAllOrders());
        return "admin/orderList";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id, Model model) {
        Order order = orderService.getOrder(id).orElseThrow(IllegalArgumentException::new);
        Map<Long, Phone> phones = order.getOrderItems().stream()
                .map(orderItem -> phoneService.get(orderItem.getPhoneId()).get())
                .collect(toMap(Phone::getId, phone -> phone));

        model.addAttribute(ATTRIBUTE_ORDER, order);
        model.addAttribute(ATTRIBUTE_PHONES, phones);
        return "admin/order";
    }

    @PostMapping("/{id}/rejected")
    public String setOrderStatusRejected(@PathVariable long id) {
        orderService.changeOrderStatus(id, OrderStatus.REJECTED);
        return "redirect:/admin/orders/" + id;
    }

    @PostMapping("/{id}/delivered")
    public String setOrderStatusDelivered(@PathVariable long id) {
        orderService.changeOrderStatus(id, OrderStatus.DELIVERED);
        return "redirect:/admin/orders/" + id;
    }
}
