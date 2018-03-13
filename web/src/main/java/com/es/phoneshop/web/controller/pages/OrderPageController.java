package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.es.phoneshop.web.constant.ControllerConstants.ORDER_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.ORDER_PAGE;

@Controller
@RequestMapping(ORDER_PAGE)
public class OrderPageController {

    private final static String ORDER_ATTRIBUTE = "order";

    private final static String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    private final static String OUT_OF_STOCK_ERROR_MESSAGE = "Some products are out of stock. " +
            "They have been removed from your shopping cart";

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart());
        model.addAttribute(ORDER_ATTRIBUTE, order);
        return ORDER_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid Order order, BindingResult bindingResult, Model model) throws OutOfStockException {
        Order orderFromCart = orderService.createOrder(cartService.getCart());
        copyProrepriesOrder(orderFromCart, order);
        if (bindingResult.hasErrors()) {
            return ORDER_PAGE_NAME;
        }

        try {
            orderService.placeOrder(order);
            cartService.clearCart();
            return "redirect:/orderOverview/" + Long.toString(order.getId());
        } catch (OutOfStockException e) {
            cartService.deleteOutOfStock();
            orderFromCart = orderService.createOrder(cartService.getCart());
            copyProrepriesOrder(orderFromCart, order);
            model.addAttribute(ERROR_MESSAGE_ATTRIBUTE, OUT_OF_STOCK_ERROR_MESSAGE);
            return ORDER_PAGE_NAME;
        }
    }

    private void copyProrepriesOrder(Order source, Order destination) {
        destination.setOrderItems(source.getOrderItems());
        destination.setTotalPrice(source.getTotalPrice());
        destination.setSubtotal(source.getSubtotal());
        destination.setDeliveryPrice(source.getDeliveryPrice());
        destination.setStatus(source.getStatus());
        destination.getOrderItems().forEach(orderItem -> orderItem.setOrder(destination));
    }
}
