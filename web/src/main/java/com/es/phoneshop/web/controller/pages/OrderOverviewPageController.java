package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartService;
import com.es.core.model.order.OrderDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    public static final String ORDER_OVERVIEW = "orderOverview";
    public static final String ORDER = "order";
    @Resource
    private OrderDao orderDao;

    @Resource
    private CartService cartService;

    @Resource
    private HttpSession session;

    @RequestMapping(value = "/{secureID}", method = RequestMethod.GET)
    public String show(@PathVariable String secureID, Model model) {
        cartService.clearCart(session);
        model.addAttribute(ORDER, orderDao.getOrderBySecureId(secureID).get());
        return ORDER_OVERVIEW;
    }

}
