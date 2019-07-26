package com.es.phoneshop.web.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String orderOverview(@RequestParam Long orderId) {
        return "orderOverview";
    }

}
