package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.dao.orderDao.OrderDao;
import com.es.core.form.order.OrderForm;
import com.es.core.model.order.Order;
import com.es.core.model.order.exception.NoSuchOrderException;
import com.es.core.service.form.order.OrderFormService;
import com.es.core.service.form.update.UpdateCartFormService;
import com.es.core.service.order.OrderService;
import com.es.core.model.stock.exception.OutOfStockException;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderFormService orderFormService;

    private final String ORDER_FORM = "orderForm";
    private final String ERROR_MESSAGE = "errorMessage";

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model){
        if(cartService.removePhonesOutOfTheStock()){
            model.addAttribute(ERROR_MESSAGE, "Some phones are out of the stock\nSo they were removed from your cart");
        }
        model.addAttribute(ORDER_FORM, orderFormService.createOrderForm(cartService.getCart()));
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Validated OrderForm orderForm, BindingResult result,
                             Model model) {
        if(result.hasErrors()){
            orderFormService.updateOrderForm(orderForm, cartService.getCart());
            return "order";
        }
        try {
            Order order = orderService.createOrder(orderForm, cartService.getCart());
            orderService.placeOrder(order);
            return "redirect:/orderOverview/" + orderDao.getOrderKey(order.getId()).
                    orElseThrow(NoSuchOrderException::new);
        }
        catch (OutOfStockException e){
            cartService.removePhonesOutOfTheStock();
            orderFormService.updateOrderForm(orderForm, cartService.getCart());

            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return "order";
        }
    }
}
