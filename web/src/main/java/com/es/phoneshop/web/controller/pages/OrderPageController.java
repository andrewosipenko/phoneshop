package com.es.phoneshop.web.controller.pages;
import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.order.EmptyOrderListException;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.bean.OrderForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.es.phoneshop.web.controller.constants.ControllerConstants.OrderPageConstants.*;


@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws PhoneInCartNotFoundException {
        Cart cart = cartService.getCart();
        model.addAttribute(CART, cart);
        model.addAttribute(ORDER_FORM, new OrderForm());
        try {
            orderService.validateCart(cart);
            model.addAttribute(TOTAL, cart.getCost().add(cart.getDeliveryPrice()));
            return "orderPage";
        } catch (OutOfStockException  e) {
            model.addAttribute(OUT_OF_STOCK_ATTRIBUTE, OUT_OF_STOCK_MESSAGE);
            model.addAttribute(REJECTED_PHONES, e.getRejectedPhones());
            model.addAttribute(TOTAL, cart.getCost().add(cart.getDeliveryPrice()));
            return "orderPage";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String  placeOrder(@ModelAttribute(ORDER_FORM) @Valid OrderForm orderForm, BindingResult bindingResult, Model model) throws PhoneInCartNotFoundException {
        if(bindingResult.hasErrors()) {
            model.addAttribute(CART, cartService.getCart());
            return "orderPage";
        }
        try {
            Order order = orderService.createOrder(cartService.getCart());
            setOrderParameters(order, orderForm);
            orderService.placeOrder(order);
            return "redirect:/orderOverview/" + order.getId();
        } catch (OutOfStockException | EmptyOrderListException e) {
            if(e instanceof OutOfStockException) {
                model.addAttribute(REJECTED_PHONES, ((OutOfStockException) e).getRejectedPhones());
                model.addAttribute(OUT_OF_STOCK_ATTRIBUTE, OUT_OF_STOCK_MESSAGE);
            }
            Cart cart = cartService.getCart();
            model.addAttribute(TOTAL, cart.getCost().add(cart.getDeliveryPrice()));
            model.addAttribute(CART, cartService.getCart());
            return "orderPage";
        }
    }

    private void setOrderParameters(Order order, OrderForm orderForm) {
        order.setFirstName(orderForm.getFirstName());
        order.setLastName(orderForm.getLastName());
        order.setContactPhoneNo(orderForm.getContactPhoneNo());
        order.setDeliveryAddress(orderForm.getDeliveryAddress());
        order.setAdditionalInfo(orderForm.getAdditionalInfo());
    }
}
