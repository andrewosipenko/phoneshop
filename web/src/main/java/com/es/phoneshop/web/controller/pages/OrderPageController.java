package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderOwnerInfo;
import com.es.core.service.cart.CartService;
import com.es.core.service.order.OrderService;
import com.es.phoneshop.web.form.OrderInfo;
import com.es.phoneshop.web.service.ErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/order")
@PropertySource("classpath:/conf/application.properties")
public class OrderPageController {
    private final static String ORDER_PAGE = "order";
    private final static String CART_PARAMETER = "cart";
    private final static String DELIVERY_PRICE_PARAMETER = "deliveryPrice";
    private final static String ORDER_INFO_PARAMETER = "orderInfo";
    private final static String ERROR_PARAMETER = "errors";

    private final static String NAME_PARAMETER = "name";
    private final static String LAST_NAME_PARAMETER = "lastName";
    private final static String ADDRESS_PARAMETER = "address";
    private final static String PHONE_PARAMETER = "phone";
    private final static String ADDITIONAL_INFO_PARAMETER = "additionalInfo";

    private final static String ORDER_OVERVIEW_PAGE = "redirect:/orderOverview/";

    @Value("${delivery.price}")
    private Float deliveryPrice;

    @Resource
    private ErrorMessageService errorMessageService;

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @Autowired
    @Qualifier("orderInfoValidator")
    private Validator orderInfoValidator;

    @Autowired
    @Qualifier("stockItemValidator")
    private Validator orderItemValidator;

    @InitBinder
    public void setUpValidators(WebDataBinder webDataBinder) {
        if (webDataBinder.getTarget() != null) {
            if (orderInfoValidator.supports(webDataBinder.getTarget().getClass())
                    && !orderInfoValidator.getClass().getName().contains("org.springframework")) {
                webDataBinder.addValidators(orderInfoValidator);
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute(CART_PARAMETER, cart);
        model.addAttribute(DELIVERY_PRICE_PARAMETER, deliveryPrice);
        model.addAttribute(ORDER_INFO_PARAMETER, new OrderInfo());
        return ORDER_PAGE;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String placeOrder(@Validated @ModelAttribute("orderInfo") OrderInfo orderInfo,
                             BindingResult bindingResult, Model model) throws IOException {
        Cart cart = cartService.getCart();
        orderItemValidator.validate(cart.getCartItems(), bindingResult);
        String redirectPage;
        if (bindingResult.hasErrors()) {
            model.addAttribute(NAME_PARAMETER,
                    errorMessageService.getErrorToField(bindingResult.getAllErrors(), NAME_PARAMETER));
            model.addAttribute(LAST_NAME_PARAMETER,
                    errorMessageService.getErrorToField(bindingResult.getAllErrors(), LAST_NAME_PARAMETER));
            model.addAttribute(ADDRESS_PARAMETER,
                    errorMessageService.getErrorToField(bindingResult.getAllErrors(), ADDRESS_PARAMETER));
            model.addAttribute(PHONE_PARAMETER,
                    errorMessageService.getErrorToField(bindingResult.getAllErrors(), PHONE_PARAMETER));
            model.addAttribute(ADDITIONAL_INFO_PARAMETER,
                    errorMessageService.getErrorToField(bindingResult.getAllErrors(), ADDITIONAL_INFO_PARAMETER));
            model.addAttribute(ERROR_PARAMETER, bindingResult.getAllErrors());
            model.addAttribute(CART_PARAMETER, cart);
            redirectPage = ORDER_PAGE;
        } else {
            OrderOwnerInfo orderOwnerInfo = new OrderOwnerInfo();
            orderOwnerInfo.setName(orderInfo.getName());
            orderOwnerInfo.setLastName(orderInfo.getLastName());
            orderOwnerInfo.setAddress(orderInfo.getAddress());
            orderOwnerInfo.setPhone(orderInfo.getPhone());
            orderOwnerInfo.setAdditionalInfo(orderInfo.getAdditionalInfo());

            Order order = orderService.createOrder(cartService.getCart(),
                    orderOwnerInfo,
                    new BigDecimal(deliveryPrice));
            cartService.clearCart(cart);
            redirectPage = ORDER_OVERVIEW_PAGE + order.getSecureId();
        }
        return redirectPage;
    }
}
