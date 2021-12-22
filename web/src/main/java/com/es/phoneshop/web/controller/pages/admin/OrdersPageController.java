package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    public static final String ADMIN_PAGE = "admin";
    public static final String ORDERS = "orders";
    public static final String ORDER = "order";
    public static final String ADMIN_ORDER_OVERVIEW = "adminOrderOverview";
    public static final String REDIRECT_ADMIN_ORDERS = "redirect:/admin/orders/";

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String showAdminPage(Model model){
        model.addAttribute(ORDERS, orderDao.getOrders());
        return ADMIN_PAGE;
    }

    @RequestMapping(value = "/{secureId}",method = RequestMethod.GET)
    public String showOrder(@PathVariable String secureId, Model model){
        model.addAttribute(ORDER, orderDao.getOrderBySecureId(secureId).get());
        return ADMIN_ORDER_OVERVIEW;
    }

    @RequestMapping(value = "/{secureId}/{status}", method = RequestMethod.POST)
    public String changeOrderStatus(@PathVariable String secureId, @PathVariable String status){
        Long orderId = orderDao.getOrderBySecureId(secureId).get().getId();
        orderService.changeStatus(orderId, OrderStatus.valueOf(status));
        return REDIRECT_ADMIN_ORDERS + secureId;
    }
}
