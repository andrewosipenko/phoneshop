package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.order.OrderService;
import com.es.core.service.stock.StockService;
import org.springframework.security.core.Authentication;
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
    private final String ATTRIBUTE_LOGIN = "login";
    private final String ATTRIBUTE_ORDERS = "orders";
    private final String ATTRIBUTE_ORDER = "order";
    private final String ATTRIBUTE_UPDATE_ORDER_STATUS = "updateOrderStatusMessage";
    private final String PAGE_ADMIN_ORDERS = "adminOrders";
    private final String PAGE_ADMIN_ORDER = "adminOrder";
    private final String REDIRECT_PAGE_ADMIN_ORDERS = "redirect:/admin/orders";

    @Resource
    private OrderService orderService;
    @Resource
    private StockService stockService;

    @RequestMapping(method = RequestMethod.GET)
    public String showOrders(Model model, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        List<Order> orders = orderService.findAll();
        model.addAttribute(ATTRIBUTE_ORDERS, orders);
        return PAGE_ADMIN_ORDERS;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    public String showOrder(@PathVariable Long orderId, Model model, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        Order order = orderService.getOrder(orderId);
        model.addAttribute(ATTRIBUTE_ORDER, order);
        return PAGE_ADMIN_ORDER;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{orderId}")
    public String setNewOrderStats(@PathVariable Long orderId,
                                   @RequestParam("orderStatus") String orderStatus,
                                   RedirectAttributes redirectAttributes){
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(orderStatus.toUpperCase()));
        Order order = orderService.getOrder(orderId);
        if (orderStatus.equals("Delivered")){
            stockService.reduceReserved(order);
        } else if (orderStatus.equals("Rejected")){
            stockService.updateStocks(order, false);
        }
        redirectAttributes.addFlashAttribute(ATTRIBUTE_UPDATE_ORDER_STATUS,
                "Order number " + orderId + " was updated with status " + orderStatus);
        return REDIRECT_PAGE_ADMIN_ORDERS;
    }
}
