package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.form.updateForm.UpdateCartForm;
import com.es.core.service.form.updateForm.UpdateCartFormService;
import com.es.core.model.phone.Phone;
import com.es.core.validation.validator.UpdateCartFormValidator;
import com.es.phoneshop.web.controller.service.phone.PhoneWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private PhoneWebService phoneWebService;
    @Resource
    private UpdateCartFormService updateCartFormService;
    @Resource
    private UpdateCartFormValidator updateCartFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(updateCartFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        List<Phone> phones = phoneWebService.getPhonesFromCart(cart);
        model.addAttribute("phones", phones);
        model.addAttribute("updateCartForm",
                updateCartFormService.getUpdateCartForm(phones, cart.getItems()));
        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String updateCart(@Validated @ModelAttribute("updateCartForm") UpdateCartForm updateCartForm, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("phones", phoneWebService.getPhonesFromCart(cartService.getCart()));
            model.addAttribute("updateCartForm", updateCartForm);
            return "cart";
        }
        cartService.update(updateCartFormService.getCartItems(updateCartForm));
        return "redirect:/cart";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public String deletePhone(@RequestParam(value = "phoneId") Long phoneId){
        cartService.remove(phoneId);
        return "redirect:/cart";
    }
}
