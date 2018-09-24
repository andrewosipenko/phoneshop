package com.es.phoneshop.web.controller;

import com.es.phoneshop.core.cart.CartService;
import com.es.phoneshop.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.forms.CartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addPhone(@Valid CartEntity cartEntity, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());

        if(cartService.isNotEnoughStock(cartEntity.getPhoneId(), cartEntity.getQuantity())) {
            bindingResult.rejectValue("quantity", "outOfStock","Out of stock");
            modelAndView.addObject("errorMsg", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        } else if(bindingResult.hasErrors()) {
            modelAndView.addObject("errorMsg", messageSource.getMessage(bindingResult.getFieldError("quantity"),null));
            return modelAndView;
        } else {
            cartService.addPhone(cartEntity.getPhoneId(), cartEntity.getQuantity());
            modelAndView.addObject("quantities", cartService.getItemsNum());
            modelAndView.addObject("overallPrice", cartService.getOverallPrice());
            modelAndView.addObject("errorMsg", "");
            return modelAndView;
        }
    }
}
