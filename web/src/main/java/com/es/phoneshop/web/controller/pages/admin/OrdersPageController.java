package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import java.util.Optional;
import static com.es.phoneshop.web.controller.constants.ControllerConstants.OrdersPageConstants.ORDER;
import static com.es.phoneshop.web.controller.constants.ControllerConstants.OrdersPageConstants.ORDERS;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderDao orderDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showOrdersList(Model model) {
        model.addAttribute(ORDERS, orderDao.findAll(0, orderDao.count()));
        return "ordersList";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    public String showOrder(@PathVariable String orderId, Model model) {
        Optional<Order> order = orderDao.get(orderId);
        model.addAttribute(ORDER, order.orElse(null));
        return "ordersDetails";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{orderId}")
    public String updateStatus(@PathVariable("orderId") String orderId, OrderStatus orderStatus) {
        orderDao.updateStatus(orderId, orderStatus);
        return "redirect:/admin/orders/" + orderId;
    }
}
