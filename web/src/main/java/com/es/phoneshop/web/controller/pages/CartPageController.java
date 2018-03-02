package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.bean.cart.CartItem;
import com.es.phoneshop.web.bean.cart.CartPhoneInfo;
import com.es.phoneshop.web.bean.cart.CartUpdateInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.es.phoneshop.web.constant.ControllerConstants.CART_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.CART_PAGE;

@Controller
@RequestMapping(CART_PAGE)
public class CartPageController {

    private final static String CART_UPDATE_INFO_ATTRIBUTE = "cartUpdateInfo";

    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        setCartItemsAttribute(model);
        return CART_PAGE_NAME;
    }

    @PutMapping(params = "update")
    public String updateCart(@Valid CartUpdateInfo cartUpdateInfo, BindingResult bindingResult, Model model) throws PhoneNotFoundException {
        if (!bindingResult.hasErrors()) {
            Map<Long, Long> mapForUpdate = cartUpdateInfo.getCartItems().stream()
                    .collect(Collectors.toMap(CartItem::getPhoneId, CartItem::getQuantity));
            cartService.update(mapForUpdate);
            return "redirect:/cart";
        }
        setPropertiesCartUpdateInfo(cartUpdateInfo);
        return CART_PAGE_NAME;
    }

    @PostMapping(params = "remove")
    public String deleteProductFormCart(@RequestParam("phoneId") Long phoneId, Model model) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

    private void setCartItemsAttribute(Model model){
        Cart cart = cartService.getCart();
        Map<Phone, Long> items = cart.getItems();
        List<Phone> phonesList = new ArrayList<>(items.keySet());
        List<CartItem> cartItems = phonesList.stream()
                .map(phone -> new CartItem(phone, items.get(phone)))
                .collect(Collectors.toList());

        model.addAttribute(CART_UPDATE_INFO_ATTRIBUTE, new CartUpdateInfo(cartItems));
    }

    private void setPropertiesCartUpdateInfo(CartUpdateInfo cartUpdateInfo){
        Cart cart = cartService.getCart();
        Map<Phone, Long> items = cart.getItems();
        Map<Long, Phone> phoneMap = items.keySet().stream().
                collect(Collectors.toMap(Phone::getId,o -> o));

        cartUpdateInfo.getCartItems()
                .forEach(cartItem -> setPropertiesCartItem(cartItem, phoneMap.get(cartItem.getPhoneId()), items));
    }

    private void setPropertiesCartItem(CartItem cartItem, Phone phone, Map<Phone, Long> items){
        if(phone != null) {
            cartItem.setBrand(phone.getBrand());
            cartItem.setImageUrl(phone.getImageUrl());
            cartItem.setModel(phone.getModel());

            BigDecimal price = phone.getPrice();
            cartItem.setPrice(price);

            BigDecimal total = price.multiply(new BigDecimal(items.get(phone)));
            cartItem.setTotal(total);
        }
    }
}
