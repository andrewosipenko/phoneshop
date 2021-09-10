package com.es.phoneshop.web.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginPageController {

    public static final String LOGIN = "login";

    @GetMapping
    public String getLoginPage() {
        return LOGIN;
    }
}