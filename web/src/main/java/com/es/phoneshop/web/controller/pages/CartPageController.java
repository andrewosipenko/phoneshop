package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.phoneshop.web.bean.cart.CartItemInfo;
import com.es.phoneshop.web.bean.cart.CartPageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("cartPageInfo" , new CartPageInfo(getCartItemInfoList(cart)));
        return "cartPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deletePhone(@RequestParam("phoneId") Long phoneId) throws PhoneInCartNotFoundException {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@ModelAttribute("cartPageInfo")@Valid CartPageInfo cartPageInfo, BindingResult bindingResult, Model model)
            throws PhoneInCartNotFoundException {
        if(bindingResult.hasErrors()) {
            model.addAttribute("cart", cartService.getCart());
            return "cartPage";
        }
            cartService.update(getCartUpdateInfoMap(cartPageInfo.getCartItemInfo()));
        return "redirect:/cart";
    }

    private List<CartItemInfo> getCartItemInfoList(Cart cart) {
        if(cart.getItems().size() == 0)
            return new ArrayList<>();
        return cart.getItems()
                .stream()
                .map(e -> new CartItemInfo(e.getPhone().getId(), e.getQuantity()))
                .collect(Collectors.toList());
    }

    private  Map<Long, Long> getCartUpdateInfoMap(List<CartItemInfo> cartItemInfo) {
        return cartItemInfo.stream()
                .collect(Collectors.toMap(CartItemInfo::getPhoneId, CartItemInfo::getQuantity));
    }
}
