package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.OutOfStockException;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.service.OrderService;
import com.es.phoneshop.core.order.throwable.EmptyCartPlacingOrderException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.web.controller.form.OrderPersonalDataForm;
import com.es.phoneshop.web.controller.throwable.InternalException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @ModelAttribute("orderPersonalDataForm")
    private OrderPersonalDataForm makeOrderPageForm() {
        return new OrderPersonalDataForm();
    }

    @ModelAttribute("cart")
    private Cart getCart() {
        return cartService.getCart();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(@ModelAttribute("orderPersonalDataForm") OrderPersonalDataForm form, BindingResult result, Model model) {
        try {
            cartService.validateStocksAndRemoveOdd();
        } catch (OutOfStockException e) {
            handleOutOfStock(e.getRejectedPhones(), result, form);
        }
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@ModelAttribute("orderPersonalDataForm") @Valid OrderPersonalDataForm form, BindingResult result) throws OutOfStockException {
        if (result.hasFieldErrors())
            return "order";
        Order order;
        try {
            cartService.validateStocksAndRemoveOdd();
            order = orderService.createOrder(cartService.getCart());
            setOrderPersonalData(order, form);
            orderService.placeOrder(order);
        } catch (OutOfStockException e) {
            handleOutOfStock(e.getRejectedPhones(), result, form);
            return "order";
        } catch (NoStockFoundException e) {
            throw new InternalException();
        } catch (EmptyCartPlacingOrderException e) {
            return "redirect:/productList";
        }
        return "redirect:/orderOverview/" + order.getId();
    }

    private void handleOutOfStock(List<Phone> rejectedPhones, BindingResult result, OrderPersonalDataForm form) {
        String rejectedPhonesString = rejectedPhones.stream()
                .map(Phone::getModel)
                .reduce("", (s1, s2) -> s1 + s2 + ", ");
        rejectedPhonesString = rejectedPhonesString.substring(0, rejectedPhonesString.length() - 2);
        Object[] args = new Object[]{rejectedPhonesString};
        result.rejectValue("stocksAvailable", "error.outOfStock", args, null);
    }

    private void setOrderPersonalData(Order order, OrderPersonalDataForm form) {
        order.setFirstName(form.getFirstName());
        order.setLastName(form.getLastName());
        order.setDeliveryAddress(form.getDeliveryAddress());
        order.setContactPhoneNo(form.getContactPhoneNo());
        order.setAdditionalInformation(form.getAdditionalInformation());
    }
}
