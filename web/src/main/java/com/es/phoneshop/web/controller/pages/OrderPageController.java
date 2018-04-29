package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.service.OrderService;
import com.es.phoneshop.core.order.throwable.OutOfStockException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.web.controller.form.OrderPageForm;
import com.es.phoneshop.web.controller.throwable.InternalException;
import org.springframework.stereotype.Controller;
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

    @ModelAttribute("orderPageForm")
    private OrderPageForm addOrderPageForm() {
        Order order = makeNewOrder();
        OrderPageForm form = new OrderPageForm();
        form.setOrder(order);
        return form;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder() throws OutOfStockException {
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@ModelAttribute("orderPageForm") @Valid OrderPageForm form, BindingResult result) throws OutOfStockException {
        if (result.hasFieldErrors())
            return "order";
        try {
            form.applyDataToAnOrder();
            orderService.placeOrder(form.getOrder());
            cartService.clear();
        } catch (OutOfStockException e) {
            handleOutOfStock(e.getRejectedPhones(), result, form);
            return "order";
        } catch (NoStockFoundException e) {
            throw new InternalException();
        }
        return "redirect:/productList";
    }

    private void handleOutOfStock(List<Phone> rejectedPhones, BindingResult result, OrderPageForm form) {
        for (Phone phone : rejectedPhones)
            cartService.remove(phone.getId());
        form.setOrder(makeNewOrder());
        String rejectedPhonesString = rejectedPhones.stream()
                .map(Phone::getModel)
                .reduce("", (s1, s2) -> s1 + s2 + ", ");
        rejectedPhonesString = rejectedPhonesString.substring(0, rejectedPhonesString.length() - 2);
        Object[] args = new Object[]{rejectedPhonesString};
        result.rejectValue("stocksAvailable", "error.outOfStock", args, null);
    }

    private Order makeNewOrder() {
        return orderService.createOrder(cartService.getRecords(), cartService.getStatus().getCostTotal());
    }
}
