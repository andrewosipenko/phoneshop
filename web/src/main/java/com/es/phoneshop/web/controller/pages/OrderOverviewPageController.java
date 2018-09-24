package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.model.order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String getOrderOverview(@ModelAttribute("order") Order order, Model model) {
        if (order.getId() != null) {
            model.addAttribute("order", order);
            return "orderOverview";
        } else {
            return "redirect:/productList";
        }
    }
}
