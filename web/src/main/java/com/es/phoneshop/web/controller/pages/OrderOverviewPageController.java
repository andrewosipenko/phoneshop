package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    private static final String ATTRIBUTE_ORDER = "order";
    private static final String ATTRIBUTE_PHONES = "phones";

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable long id, Model model) {
        Order order = orderService.getOrder(id).orElseThrow(IllegalArgumentException::new);
        Map<Long, Phone> phones = order.getOrderItems().stream()
                .map(orderItem -> phoneService.get(orderItem.getPhoneId()).get())
                .collect(toMap(Phone::getId, phone -> phone));
        cartService.emptyCart();

        model.addAttribute(ATTRIBUTE_ORDER, order);
        model.addAttribute(ATTRIBUTE_PHONES, phones);
        return "orderOverview";
    }
}
