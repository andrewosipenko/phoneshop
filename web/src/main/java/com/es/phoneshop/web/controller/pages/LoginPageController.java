package com.es.phoneshop.web.controller.pages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginPageController {
    @Value("${login.failure}")
    private String loginFailureMessage;

    @RequestMapping(method = RequestMethod.GET)
    public String showLoginPage(Model model, @RequestParam(required = false) Object error) {
        if (error != null)
            model.addAttribute("loginError", loginFailureMessage);
        return "login";
    }
}
