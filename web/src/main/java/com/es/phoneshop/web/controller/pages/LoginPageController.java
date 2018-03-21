package com.es.phoneshop.web.controller.pages;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.es.phoneshop.web.constant.ControllerConstants.LOGIN_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerConstants.ORDERS_LIST_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.LOGIN_PAGE;

@Controller
@RequestMapping(LOGIN_PAGE)
public class LoginPageController {

    @GetMapping
    public String getLoginPage() {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/" + ORDERS_LIST_PAGE_NAME;
        }
        return LOGIN_PAGE_NAME;
    }
}
