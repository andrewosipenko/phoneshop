package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartItem;
import com.es.core.model.cart.CartService;
import com.es.phoneshop.web.controller.pages.dto.QuantityForm;
import com.es.phoneshop.web.controller.pages.dto.UpdateCartRequest;
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
    public static final String CART = "cart";
    public static final String UPDATE_CART_FORM = "updateCartForm";
    public static final String PRODUCT_CART = "productCart";
    public static final String REDIRECT_CART = "redirect:/cart";
    public static final String DELETE_CART_ITEM_URL = "/deleteCartItem";
    public static final String UPDATE_URL = "/update";
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute(CART, cartService.getCart());
        model.addAttribute(UPDATE_CART_FORM, createUpdateCartForm());
        return PRODUCT_CART;
    }

    @RequestMapping(value = DELETE_CART_ITEM_URL, method = RequestMethod.GET)
    public String deleteItem(long phoneId) {
        cartService.remove(phoneId);
        return REDIRECT_CART;
    }

    @RequestMapping(value = UPDATE_URL, method = RequestMethod.POST)
    public String updateCart(@Valid UpdateCartRequest updateCartForm, BindingResult result, Model model) {
        Map<Long, Long> updateMap = new HashMap<>();
        if (result.hasErrors()) {
            model.addAttribute(CART, cartService.getCart());
            return PRODUCT_CART;
        }
        updateCartForm.getQuantityFormMap().forEach((id, quantityForm) -> updateMap.put(id, quantityForm.getQuantity()));
        cartService.update(updateMap);
        return REDIRECT_CART;
    }

    private UpdateCartRequest createUpdateCartForm() {
        List<CartItem> cartItemList = cartService.getCart().getCartItems();
        Map<Long, QuantityForm> quantityFormMap = new HashMap<>();
        cartItemList.forEach(cartItem ->
                quantityFormMap.put(cartItem.getPhone().getId(), new QuantityForm(cartItem.getQuantity().longValue())));
        UpdateCartRequest updateCartForm = new UpdateCartRequest();
        updateCartForm.setQuantityFormMap(quantityFormMap);
        return updateCartForm;
    }
}
