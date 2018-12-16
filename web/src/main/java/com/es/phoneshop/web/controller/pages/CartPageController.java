package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.services.cart.CartService;
import com.es.core.services.cart.TotalPriceService;
import com.es.core.services.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private static final String REDIRECTING_ADDRESS = "redirect:/cart";
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

    @PutMapping
    public String updateCart(HttpServletRequest request) {
        cartService.update(resolveFormData(request));
        return REDIRECTING_ADDRESS;
    }

    @PostMapping
    public String deleteItem(@RequestParam(value = "delete") Long phoneIdForDelete) {
        cartService.remove(phoneIdForDelete);
        return REDIRECTING_ADDRESS;
    }

    Map<Long, Integer> resolveFormData(HttpServletRequest request) {
        Map<Long, Integer> result = new HashMap<>();
        Map<String, String[]> params = request.getParameterMap();
        params.forEach((paramName, values) -> {
            if (paramName.contains("quantity")) {
                result.put(Long.parseLong(paramName.substring("quantity".length())), Integer.parseInt(values[0]));
            }
        });
        return result;
    }
}
