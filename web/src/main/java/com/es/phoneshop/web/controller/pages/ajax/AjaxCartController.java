package com.es.phoneshop.web.controller.pages.ajax;

import com.es.core.cart.CartService;
import com.es.core.form.AddToCartForm;
import com.es.core.model.phone.exception.InvalidAddToCartFormException;
import com.es.core.validation.validator.AddToCartFormValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;
    @Resource
    private AddToCartFormValidator addToCartFormValidator;
    @Resource
    private MessageCodeResolveService messageCodeResolveService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(addToCartFormValidator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView addPhone(@Validated AddToCartForm form, BindingResult result) throws InvalidAddToCartFormException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        if(result.hasErrors()){
            String errorMessage = messageCodeResolveService.getErrorMessage(result);
            throw new InvalidAddToCartFormException(errorMessage);
        }
        else {
            cartService.addPhone(form.getPhoneId(), form.getQuantity());
            mv.addObject("itemsAmount", cartService.getCart().getItemsAmount());
            mv.addObject("subtotal", cartService.getCart().getSubtotal());
            mv.setStatus(HttpStatus.OK);
        }
        return mv;
    }

    @ExceptionHandler(InvalidAddToCartFormException.class)
    public @ResponseBody @ResponseStatus(HttpStatus.BAD_REQUEST) String handleInvalidAddToCartException(InvalidAddToCartFormException e){
        return e.getMessage();
    }
}