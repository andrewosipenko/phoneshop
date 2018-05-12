package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import java.util.Optional;
import static com.es.phoneshop.web.controller.constants.ControllerConstants.OrderPageConstants.ORDER;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private OrderDao orderDao;

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    public String showProductDetails(@PathVariable String orderId, Model model) {
        Optional<Order> order = orderDao.get(orderId);
        model.addAttribute(ORDER, order.orElse(null));
        return "orderOverview";
    }
}
