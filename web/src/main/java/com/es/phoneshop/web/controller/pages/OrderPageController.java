package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.UserContactInfo;
import com.es.phoneshop.web.controller.dto.UserContactInfoRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    public static final String ORDER = "order";

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showOrderPage(Model model) {
        Order order = orderService.createOrder(cartService.getCart(), null);
        model.addAttribute(ORDER, order);
        model.addAttribute("userContactInfoRequest", new UserContactInfoRequest());
        return ORDER;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid UserContactInfoRequest userContactInfoRequest,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) throws OutOfStockException {
        System.out.println(userContactInfoRequest);
        Order order = orderService.createOrder(cartService.getCart(), createUserContactInfo(userContactInfoRequest));
        if (result.hasErrors()) {
            model.addAttribute(ORDER, order);
            return "order";
        }
        System.out.println(userContactInfoRequest.getFirstName());
        System.out.println(userContactInfoRequest.getLastName());
        orderService.placeOrder(order);
        redirectAttributes.addAttribute("orderId", order.getId());
        return "redirect:/orderOverview/{orderId}";
    }

    private UserContactInfo createUserContactInfo(UserContactInfoRequest request){
        UserContactInfo info = new UserContactInfo();
        info.setContactPhoneNo(request.getContactPhoneNo());
        info.setDeliveryAddress(request.getDeliveryAddress());
        info.setFirstName(request.getFirstName());
        info.setLastName(request.getLastName());
        info.setAdditionalInfo(request.getAdditionalInfo());
        return info;
    }
}
