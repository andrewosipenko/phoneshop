package com.es.phoneshop.web.controller.ajax;

import com.es.core.cart.CartService;
import com.es.core.model.phone.AddToCartForm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView addPhone(@Validated AddToCartForm form, BindingResult result) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        if(result.hasErrors()){
            mv.setStatus(HttpStatus.BAD_REQUEST);
        }
        else {
            cartService.addPhone(form.getPhoneId(), form.getQuantity());
            mv.addObject("itemsAmount", cartService.getCart().getItemsAmount());
            mv.addObject("subtotal", cartService.getCart().getSubtotal());
            mv.setStatus(HttpStatus.OK);
        }
        return mv;
    }
}