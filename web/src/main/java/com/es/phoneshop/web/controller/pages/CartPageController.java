package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartItem;
import com.es.core.model.cart.CartService;
import com.es.phoneshop.web.controller.pages.dto.QuantityForm;
import com.es.phoneshop.web.controller.pages.dto.UpdateCartForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("updateCartForm", createUpdateCartForm());
        return "productCart";
    }

    @RequestMapping(value = "/deleteCartItem", method = RequestMethod.GET)
    public String deleteItem(long phoneId) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCart(@Valid UpdateCartForm updateCartForm, BindingResult result, Model model) {
        Map<Long, Long> updateMap = new HashMap<>();
        if (result.hasErrors()) {
            model.addAttribute("cart", cartService.getCart());
            return "productCart";
        }
        updateCartForm.getQuantityFormMap().forEach((id, quantityForm) -> {
            updateMap.put(id, quantityForm.getQuantity());
        });
        cartService.update(updateMap);
        return "redirect:/cart";
    }

    private UpdateCartForm createUpdateCartForm() {
        List<CartItem> cartItemList = cartService.getCart().getCartItems();
        Map<Long, QuantityForm> quantityFormMap = new HashMap<>();
        cartItemList.forEach(cartItem ->
                quantityFormMap.put(cartItem.getPhone().getId(), new QuantityForm(cartItem.getQuantity().longValue())));
        UpdateCartForm updateCartForm = new UpdateCartForm();
        updateCartForm.setQuantityFormMap(quantityFormMap);
        return updateCartForm;
    }
}
