package com.es.phoneshop.web.controller;

import com.es.core.exceptions.cart.AddToCartException;
import com.es.core.form.cart.AddCartForm;
import com.es.core.service.cart.CartService;
import com.es.core.validator.AddCartValidator;
import com.es.phoneshop.web.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;
    @Resource
    private AddCartValidator cartValidator;
    @Resource
    private MessageService messageService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(cartValidator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView addPhone(@Validated @RequestBody AddCartForm cartForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        if (bindingResult.hasErrors()) {
            String errorMessage = messageService.getErrorMessage(bindingResult);
            throw new AddToCartException(errorMessage);
        } else {
            cartService.addPhone(cartForm.getPhoneId(), cartForm.getQuantity());
            modelAndView.addObject("subtotal", cartService.getCart().getSubtotal());
            modelAndView.addObject("cartAmount", cartService.getCart().getCartAmount());
            modelAndView.setStatus(HttpStatus.OK);
        }
        return modelAndView;
    }

    @ExceptionHandler(AddToCartException.class)
    public @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Add to cart error")
    String handleAddToCartException(AddToCartException ex) {
        return ex.getMessage();
    }
}
