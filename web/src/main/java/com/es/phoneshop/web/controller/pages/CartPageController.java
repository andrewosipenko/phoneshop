package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.CartService;
import com.es.core.validator.CartForm;
import com.es.core.validator.CartValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("cartForm", convertCart(cart));
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(CartForm cartForm, BindingResult bindingResult, Model model) {
        validator.validate(cartForm, bindingResult);
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            model.addAttribute("errors", allErrors);
        } else {
            cartService.update(convertCartForm(cartForm));
        }
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("cartForm", cartForm);
        return "cart";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteCartItem(@RequestParam Long phoneId, Model model) {
        cartService.remove(phoneId);
        model.addAttribute("cart", cartService.getCart());
        return "redirect:cart";
    }

    private CartForm convertCart(Cart cart) {
        CartForm cartForm = new CartForm();
        cartForm.setPhoneIds(cart.getItems().stream().map(CartItem::getPhone)
                .map(Phone::getId)
                .map(String::valueOf)
                .collect(Collectors.toList()));
        cartForm.setQuantities(cart.getItems().stream().map(CartItem::getQuantity)
                .map(String::valueOf)
                .collect(Collectors.toList()));
        return cartForm;
    }

    private List<CartItem> convertCartForm(CartForm cartForm) {
        List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < cartForm.getPhoneIds().size(); i++) {
            Phone phone = phoneDao.get(Long.parseLong(cartForm.getPhoneIds().get(i))).get();
            CartItem cartItem = new CartItem(phone, Long.parseLong(cartForm.getQuantities().get(i)));
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
