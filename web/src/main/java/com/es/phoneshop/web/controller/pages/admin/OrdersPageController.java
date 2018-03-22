package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.exception.OrderNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.order.OrderService;
import com.es.phoneshop.web.constant.ControllerMapping.AdminPanel;
import com.es.phoneshop.web.exception.BadRequestException;
import com.es.phoneshop.web.exception.PageNotFoundException;
import com.es.phoneshop.web.service.order.OrdersPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.es.phoneshop.web.constant.ControllerConstants.ORDERS_LIST_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerConstants.ORDER_DETAILS_PAGE_NAME;

@Controller
@RequestMapping(AdminPanel.ORDERS_PAGE)
public class OrdersPageController {

    private final static String PAGE_NUMBER_REQUEST_PARAM = "page";

    private final static String ORDER_ATTRIBUTE = "order";

    private final static String ORDERS_PAGE_ATTRIBUTE = "ordersPage";

    @Resource
    private OrderService orderService;

    @Resource
    private OrdersPageService ordersPageService;


    @GetMapping
    public String showOrderList(@RequestParam(name = PAGE_NUMBER_REQUEST_PARAM, defaultValue = "1") int pageNumber,
                                Model model) {
        model.addAttribute(ORDERS_PAGE_ATTRIBUTE, ordersPageService.getOrdersPage(pageNumber));
        return ORDERS_LIST_PAGE_NAME;
    }

    @GetMapping(AdminPanel.ORDER_DETAILS_PAGE)
    public String getOrderDetails(@PathVariable("id") Long id, Model model) throws PageNotFoundException {
        Order order = orderService.getOrder(id).orElseThrow(PageNotFoundException::new);
        model.addAttribute(ORDER_ATTRIBUTE, order);
        return ORDER_DETAILS_PAGE_NAME;
    }

    @PostMapping(AdminPanel.ORDER_DETAILS_PAGE)
    public String setOrderStatus(@PathVariable("id") Long id, OrderStatus status, Model model)
            throws PageNotFoundException, BadRequestException {

        try {
            orderService.updateOrderStatus(id, status);
        } catch (OrderNotFoundException e) {
            throw new PageNotFoundException(e);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }

        return "redirect:/admin/orders/" + Long.toString(id);
    }
}
