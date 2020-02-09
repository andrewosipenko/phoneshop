package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.exceptions.CustomNotFoundException;
import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model) {
        model.addAttribute("phones", phoneService.findAll(0, 10));
        return "productList";
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ModelAndView handleException(CustomNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("errorPages/notFoundPhone");
        modelAndView.addObject("errCode", exception.getErrCode());
        modelAndView.addObject("errMsg", exception.getErrMsg());
        return modelAndView;
    }
}
