package com.es.phoneshop.web.controller.pages;

import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @Resource
    private OrderService orderService;

    @GetMapping("/{orderUUID}")
    public String getOrder(
            @PathVariable String orderUUID,
            Model model){
        model.addAttribute( "order", orderService.getOrderByUUID(UUID.fromString(orderUUID)));
        return "orderOverview";
    }

}
