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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    public static final String ORDER = "order";
    public static final String USER_CONTACT_INFO_REQUEST = "userContactInfoRequest";
    public static final String REDIRECT_ORDER_OVERVIEW = "redirect:/orderOverview/{secureId}";
    public static final String ORDER_SECURE_ID = "secureId";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String ERROR_MESSAGE_TEXT = "Some positions in your order are no longer available, they were removed from the card.";

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @Resource
    private HttpSession session;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showOrderPage(Model model) {
        Order order = orderService.createOrder(cartService.getCart(session), null);
        model.addAttribute(ORDER, order);
        model.addAttribute(USER_CONTACT_INFO_REQUEST, new UserContactInfoRequest());
        return ORDER;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid UserContactInfoRequest userContactInfoRequest,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart(session), createUserContactInfo(userContactInfoRequest));
        if (result.hasErrors()) {
            model.addAttribute(ORDER, order);
            return ORDER;
        }
        try {
            orderService.placeOrder(order, session);
        } catch (OutOfStockException e) {
            model.addAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            model.addAttribute(ORDER, order);
            return ORDER;
        }
        redirectAttributes.addAttribute(ORDER_SECURE_ID, order.getSecureId());
        return REDIRECT_ORDER_OVERVIEW;
    }

    private UserContactInfo createUserContactInfo(UserContactInfoRequest request) {
        UserContactInfo info = new UserContactInfo();
        info.setContactPhoneNo(request.getContactPhoneNo());
        info.setDeliveryAddress(request.getDeliveryAddress());
        info.setFirstName(request.getFirstName());
        info.setLastName(request.getLastName());
        info.setAdditionalInfo(request.getAdditionalInfo());
        return info;
    }
}
