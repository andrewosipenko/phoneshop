package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.model.cart.CartList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    private static final String ATTRIBUTE_ITEMS = "items";

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cartList", new CartList(cartService.getAllItems()));
        model.addAttribute(ATTRIBUTE_ITEMS, cartService.getAllItems());
        return "cart";
    }

    @PostMapping(params = "update")
    public String updateCart(@Valid @ModelAttribute("cartList") CartList cartList,
                             BindingResult bindingResult,
                             Model model) {
        if (!bindingResult.hasErrors()) {
            cartService.update(cartList.getAllPhonesWithQuantity());
            return "redirect:/cart";
        }
        model.addAttribute(ATTRIBUTE_ITEMS, cartService.getAllItems());
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteItemFromCart(@PathVariable long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNonexistentElement() {
        return "redirect:/cart";
    }
}
