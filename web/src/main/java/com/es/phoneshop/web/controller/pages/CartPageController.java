package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.services.cart.CartService;
import com.es.core.services.cart.TotalPriceService;
import com.es.core.services.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private TotalPriceService totalPriceService;
    @Resource
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        List<CartItem> cartItems = cartService.getCart().getCartItems();
        List<Phone> phones = new ArrayList<>();
        model.addAttribute("cartItemsAmount", cartService.getQuantityOfProducts());
        model.addAttribute("cartItemsPrice", totalPriceService.getTotalPriceOfProducts());
        cartItems.forEach(cartItem -> phones.add(phoneService.get(cartItem.getPhoneId()).get()));
        model.addAttribute("phones", phones);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart() {
        cartService.update(null);
    }
}
