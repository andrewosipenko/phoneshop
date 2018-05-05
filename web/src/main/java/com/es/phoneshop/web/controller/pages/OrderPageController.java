package com.es.phoneshop.web.controller.pages;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.order.EmptyOrderListException;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.es.phoneshop.web.controller.constants.ControllerConstants.OrderPageConstants.ORDER;
import static com.es.phoneshop.web.controller.constants.ControllerConstants.OrderPageConstants.OUT_OF_STOCK_ATTRIBUTE;
import static com.es.phoneshop.web.controller.constants.ControllerConstants.OrderPageConstants.OUT_OF_STOCK_MESSAGE;


@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart());
        model.addAttribute(ORDER, order);
        return "orderPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String  placeOrder(@ModelAttribute(ORDER) @Valid Order order,BindingResult bindingResult, Model model) throws PhoneInCartNotFoundException {
        Order orderFromCart = orderService.createOrder(cartService.getCart());
        setOrderInfo(orderFromCart, order);
        if(bindingResult.hasErrors()) {
            return "orderPage";
        }
        try {
            orderService.placeOrder(order);
            return "redirect:/orderOverview/" + Long.toString(order.getId());
        } catch (OutOfStockException | EmptyOrderListException e) {
            model.addAttribute(OUT_OF_STOCK_ATTRIBUTE, OUT_OF_STOCK_MESSAGE);
            return "orderPage";
        }
    }

    private void setOrderInfo(Order from, Order to) {
        to.setOrderItems(from.getOrderItems());
        to.setTotalPrice(from.getTotalPrice());
        to.setSubtotal(from.getSubtotal());
        to.setDeliveryPrice(from.getDeliveryPrice());
        to.setStatus(from.getStatus());
        to.getOrderItems().forEach(orderItem -> orderItem.setOrder(to));
    }
}
