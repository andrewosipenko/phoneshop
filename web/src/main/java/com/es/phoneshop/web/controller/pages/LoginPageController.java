package com.es.phoneshop.web.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.es.phoneshop.web.constant.ControllerConstants.LOGIN_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.AdminPanel.ORDERS_PAGE;
import static com.es.phoneshop.web.constant.ControllerMapping.LOGIN_PAGE;
import static com.es.phoneshop.web.controller.util.AuthUtil.isAuthenticated;

@Controller
@RequestMapping(LOGIN_PAGE)
public class LoginPageController {

    @GetMapping
    public String getLoginPage() {
        if (isAuthenticated()) {
            return "redirect:" + ORDERS_PAGE;
        }
        return LOGIN_PAGE_NAME;
    }
}
