package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.cart.CartService;
import com.es.phoneshop.core.model.order.Order;
import com.es.phoneshop.core.order.OrderService;
import com.es.phoneshop.web.controller.forms.ClientInformationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private final static String ERROR_MESSAGE="Some item(s) in your cart were out of stock and got deleted";

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) {
       generateOrderPage(model, new ClientInformationForm());
       return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@ModelAttribute("clientInformationForm") @Valid ClientInformationForm form, BindingResult result, Model model,
                             RedirectAttributes attributes) {
        if(result.hasErrors()) {
            generateOrderPage(model, form);
            return "order";
        } else if (!orderService.checkOrderItemsStock()) {
            model.addAttribute("errorMsg", ERROR_MESSAGE);
            generateOrderPage(model, form);
            return "order";
        } else {
            Order order = orderService.createOrder();
            setClientInfo(order, form);
            orderService.placeOrder(order);
            attributes.addFlashAttribute("order", order);
            return "redirect:/orderOverview";
        }
    }

    private void generateOrderPage(Model model, ClientInformationForm form) {
        BigDecimal subtotal = new BigDecimal(cartService.getOverallPrice());
        BigDecimal deliveryPrice = new BigDecimal(orderService.getDeliveryPrice());
        BigDecimal totalPrice = subtotal.add(deliveryPrice);
        model.addAttribute("phoneEntries", cartService.getPhoneMap().entrySet());
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("deliveryPrice", deliveryPrice);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("clientInformationForm", form);
    }

    private void setClientInfo(Order order, ClientInformationForm form) {
        order.setFirstName(form.getFirstName());
        order.setLastName(form.getLastName());
        order.setContactPhoneNo(form.getContactPhoneNo());
        order.setDeliveryAddress(form.getDeliveryAddress());
        order.setAdditionalInfo(form.getAdditionalInfo());
    }
}
