package com.es.phoneshop.web.controller.pages;

import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.pages.cart.CartItemsForUpdate;
import com.es.phoneshop.web.controller.pages.cart.UpdateCartValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;


@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Resource
    private CartService cartService;

    @Resource
    private UpdateCartValidator updateCartValidator;


    @InitBinder("cartItemsForUpdate")
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateCartValidator);
    }

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("infoCart", cartService.getInfoCart());
        model.addAttribute("cartItemsForUpdate", new CartItemsForUpdate());
        return "cartPage";
    }

    @PostMapping("/update")
    public String updateCart(@ModelAttribute @Validated CartItemsForUpdate cartItemsForUpdate,
                           BindingResult bindingResult, Model model) {
        boolean containErrors = bindingResult.hasErrors();
        model.addAttribute("errors", containErrors);
        if(!containErrors) {
            Map<Long, Long> cartItems = cartService.formMapForUpdate(cartItemsForUpdate.getCartItems());
            cartService.update(cartItems);
        }
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("infoCart", cartService.getInfoCart());
        return "cartPage";
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id, Model model) {
        cartService.remove(id);
        return "redirect:/cart";
    }
}
