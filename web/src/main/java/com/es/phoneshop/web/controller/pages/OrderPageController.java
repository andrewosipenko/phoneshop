package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.cart.cost.CostService;
import com.es.core.model.order.Person;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.model.order.Order;
import com.es.phoneshop.web.model.order.PersonInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Locale;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private CartService cartService;

    @Resource
    private CostService costService;

    @Resource
    private OrderService orderService;

    @Resource
    private MessageSource messageSource;

    @Value("${delivery.price}")
    private BigDecimal delivery;

    private static final String ATTRIBUTE_ORDER = "order";
    private static final String ATTRIBUTE_PERSON_INFO = "personInfo";
    private static final String ATTRIBUTE_NO_PRODUCTS = "noProducts";

    private static final String MESSAGE_NO_PRODUCTS = "noProducts";

    @GetMapping
    public String getOrder(Model model) {
        model.addAttribute(ATTRIBUTE_ORDER, createOrder());
        model.addAttribute(ATTRIBUTE_PERSON_INFO, new PersonInfo());
        return "order";
    }

    @PostMapping
    public String placeOrder(@Valid @ModelAttribute(ATTRIBUTE_PERSON_INFO) PersonInfo personInfo,
                             BindingResult bindingResult,
                             Model model,
                             Locale locale) {
        model.addAttribute(ATTRIBUTE_PERSON_INFO, personInfo);

        if (bindingResult.hasErrors()) {
            model.addAttribute(ATTRIBUTE_ORDER, createOrder());
            return "order";
        }

        long orderId;
        try {
            orderId = orderService.placeNewOrderAndReturnId(cartService.getCart(), createPerson(personInfo));
        } catch (OutOfStockException e) {
            cartService.removeProductsWhichNoInStock();
            model.addAttribute(ATTRIBUTE_ORDER, createOrder());
            model.addAttribute(ATTRIBUTE_NO_PRODUCTS, messageSource.getMessage(MESSAGE_NO_PRODUCTS, null, locale));
            return "order";
        }

        return "redirect:/orderOverview/" + orderId;
    }

    private Order createOrder() {
        Order order = new Order();
        BigDecimal subtotal = costService.getCost(cartService.getCart());
        order.setItems(cartService.getAllItems());
        order.setSubtotal(subtotal);
        order.setDelivery(delivery);
        order.setTotal(subtotal.add(delivery));
        return order;
    }

    private Person createPerson(PersonInfo personInfo) {
        Person person = new Person();
        person.setFirstName(personInfo.getFirstName());
        person.setLastName(personInfo.getLastName());
        person.setAddress(personInfo.getAddress());
        person.setPhone(personInfo.getPhone());
        person.setAdditionalInfo(personInfo.getAdditionalInfo());
        return person;
    }
}
