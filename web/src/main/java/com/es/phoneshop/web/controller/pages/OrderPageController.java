package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes("order")
public class OrderPageController {

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;


    @GetMapping
    public String getOrder(Model model) throws OutOfStockException {
        Cart cart = cartService.getCart();
        if(cart.getProducts().isEmpty()){
            return "redirect:cart";
        }

        Order order = orderService.createOrder(cart);
        model.addAttribute("order", order);
        return "orderPage";
    }

    @PostMapping
    public String placeOrder(
            @ModelAttribute @Valid Order order,
            BindingResult bindingResult) throws OutOfStockException {
        if(bindingResult.hasErrors()){
            if(bindingResult.hasFieldErrors("orderItems[*")) {
                processOutOfStockErrors(order, bindingResult);
            }
            return "orderPage";
        } else {
            orderService.placeOrder(order);
            return "redirect:orderOverview/"+order.getOrderUUID();
        }
    }

    private void processOutOfStockErrors(Order order, BindingResult bindingResult){
        List<OrderItem> rejectedItems = bindingResult.getFieldErrors("orderItems[*").stream()
                .map(FieldError::getRejectedValue)
                .map((itemObject) -> (OrderItem)itemObject)
                .collect(Collectors.toList());

        orderService.removeItems(order, rejectedItems);
        cartService.removeEntries(rejectedItems.stream()
                .map((item)->item.getPhone().getId())
                .collect(Collectors.toList())
        );
    }
}
