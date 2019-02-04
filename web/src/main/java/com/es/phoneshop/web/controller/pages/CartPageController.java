package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.cart.AddToCartException;
import com.es.core.form.cart.CartForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
import com.es.core.validator.UpdateCartValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    private final String ATTRIBUTE_LOGIN = "login";
    private final String ATTRIBUTE_PHONES = "phones";
    private final String ATTRIBUTE_UPDATE_CART_FORM = "updateCartForm";
    private final String ATTRIBUTE_ERRORS = "errors";
    private final String PAGE_CART = "cart";
    private final String REDIRECT_PAGE_CART = "redirect:/cart";

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
    public String getCart(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        Cart cart = cartService.getCart();
        List<Phone> phones = phoneService.getPhoneListFromCart(cart);
        model.addAttribute(ATTRIBUTE_PHONES, phones);
        CartForm updateCartForm = cartService.getUpdateCart(phones, cart.getCartItems());
        model.addAttribute(ATTRIBUTE_UPDATE_CART_FORM, updateCartForm);
        return PAGE_CART;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String updateCart(@Validated @ModelAttribute(value = "updateCartForm") CartForm updateCartForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            String[] errors = new String[updateCartForm.getCartFormList().size()];
            for (ObjectError error : bindingResult.getAllErrors()) {
                FieldError fieldError = (FieldError) error;
                String field = fieldError.getField();
                field = field.substring(field.indexOf('[') + 1, field.indexOf(']'));
                errors[Integer.parseInt(field)] = MESSAGE_WRONG_FORMAT;
            }
            model.addAttribute(ATTRIBUTE_ERRORS, errors);
            Cart cart = cartService.getCart();
            List<Phone> phones = phoneService.getPhoneListFromCart(cart);
            model.addAttribute(ATTRIBUTE_PHONES, phones);
            model.addAttribute(ATTRIBUTE_UPDATE_CART_FORM, updateCartForm);
            return PAGE_CART;
        }
        cartService.update(cartService.getItemsCart(updateCartForm));
        return REDIRECT_PAGE_CART;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public String deleteCart(@RequestParam(value = "phoneId") Long phoneId) {
        cartService.remove(phoneId);
        return REDIRECT_PAGE_CART;
    }

    @ExceptionHandler(AddToCartException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleAddToCartException(AddToCartException ex) {
        return ex.getMessage();
    }
}
