package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.bean.cart.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.es.phoneshop.web.constant.ControllerConstants.CART_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.CART_PAGE;

@Controller
@RequestMapping(CART_PAGE)
public class CartPageController {

    private final static String CART_ITEM_LIST_ATTRIBUTE = "cartItemList";

    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        Map<Phone, Long> items = cart.getItems();
        List<Phone> phonesList = new ArrayList<>(items.keySet());
        List<CartItem> cartItems = phonesList.stream()
                .map(phone -> new CartItem(phone, items.get(phone)))
                .collect(Collectors.toList());
        model.addAttribute(CART_ITEM_LIST_ATTRIBUTE, cartItems);
        return CART_PAGE_NAME;
    }

    @PutMapping(params = "update")
    public String updateCart() throws PhoneNotFoundException {
        return "redirect:cart";
    }

    @PostMapping(params = "remove")
    public String deleteProductFormCart(@RequestParam Long phoneId) throws PhoneNotFoundException {
        cartService.remove(phoneId);
        return "redirect:cart";
    }
}
