package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.cart.AddToCartException;
import com.es.core.form.cart.UpdateCartForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
import com.es.core.validator.UpdateCartValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private final String MESSAGE_WRONG_FORMAT = "Wrong format";

    @Resource
    private CartService cartService;
    @Resource
    private UpdateCartValidator updateCartValidator;
    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(updateCartValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        List<Phone> phones = phoneService.getPhoneListFromCart(cart);
        model.addAttribute("phones", phones);
        UpdateCartForm updateCartForm = cartService.getUpdateCart(phones, cart.getCartItems());
        model.addAttribute("updateCartForm", updateCartForm);
        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String updateCart(@Validated @ModelAttribute(value = "updateCartForm") UpdateCartForm updateCartForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                FieldError fieldError = (FieldError) error;
                String field = fieldError.getField();
                field = field.substring(field.indexOf('[') + 1, field.indexOf(']'));
                model.addAttribute("error" + field, MESSAGE_WRONG_FORMAT);
            }
            Cart cart = cartService.getCart();
            List<Phone> phones = phoneService.getPhoneListFromCart(cart);
            model.addAttribute("phones", phones);
            model.addAttribute("updateCartForm", updateCartForm);
            return "cart";
        }
        cartService.update(cartService.getItemsCart(updateCartForm));
        return "redirect:/cart";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public String deleteCart(@RequestParam(value = "phoneId") Long phoneId) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

    @ExceptionHandler(AddToCartException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleAddToCartException(AddToCartException ex) {
        return ex.getMessage();
    }
}
