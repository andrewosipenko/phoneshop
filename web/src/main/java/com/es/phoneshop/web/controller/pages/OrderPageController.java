package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import com.es.core.model.stock.exception.OutOfStockException;
import com.es.core.service.order.orderItem.OrderItemService;
import com.es.phoneshop.web.controller.service.phone.PhoneWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private CartService cartService;
    @Resource
    private PhoneWebService phoneWebService;

    private final String ORDER = "order";
    private final String ERROR_MESSAGE = "errorMessage";

    @ModelAttribute(name = ORDER)
    public Order setOrder(){
        return orderService.createOrder(cartService.getCart());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(){
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Validated @ModelAttribute(ORDER) Order order, BindingResult result,
                             Model model) {
        if(result.hasErrors()){
            return "order";
        }
        try {
            orderService.placeOrder(order);
            cartService.clearCart();
            return "redirect:/orderOverview/" + order.getId();
        }
        catch (OutOfStockException e){
            Cart cart = cartService.getCart();
            cartService.removePhonesOutOfTheStock(phoneWebService.getPhonesFromCart(cart));
            orderItemService.setNewOrderItems(order, cart);

            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return "order";
        }
    }
}
