package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.bean.cart.CartDisplayInfo;
import com.es.phoneshop.web.bean.cart.CartDisplayItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.es.phoneshop.web.constant.ControllerConstants.CART_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.CART_PAGE;

@Controller
@RequestMapping(CART_PAGE)
public class CartPageController {

    private final static String CART_UPDATE_INFO_ATTRIBUTE = "cartDisplayInfo";

    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        setCartItemsAttribute(model);
        return CART_PAGE_NAME;
    }

    @PutMapping(params = "update")
    public String updateCart(@Valid CartDisplayInfo cartDisplayInfo, BindingResult bindingResult) throws PhoneNotFoundException {
        if (bindingResult.hasErrors()) {
            if (cartDisplayInfo.getCartDisplayItems() != null) {
                setPropertiesCartUpdateInfo(cartDisplayInfo);
            }
            return CART_PAGE_NAME;
        }

        Map<Long, Long> mapForUpdate = cartDisplayInfo.getCartDisplayItems().stream()
                .collect(Collectors.toMap(CartDisplayItem::getPhoneId, CartDisplayItem::getQuantity));
        cartService.update(mapForUpdate);
        return "redirect:/cart";
    }

    @PostMapping(params = "remove")
    public String deleteProductFormCart(@RequestParam("phoneId") Long phoneId) {
        cartService.updateOrDelete(phoneId, 0L);
        return "redirect:/cart";
    }

    private void setCartItemsAttribute(Model model) {
        Cart cart = cartService.getCart();
        List<CartDisplayItem> cartDisplayItems = cart.getItems().stream()
                .map(item -> new CartDisplayItem(item.getPhone(), item.getQuantity()))
                .collect(Collectors.toList());

        model.addAttribute(CART_UPDATE_INFO_ATTRIBUTE, new CartDisplayInfo(cartDisplayItems));
    }

    private void setPropertiesCartUpdateInfo(CartDisplayInfo cartDisplayInfo) {
        Cart cart = cartService.getCart();
        Map<Long, CartItem> items = cart.getItems().stream()
                .collect(Collectors.toMap(item -> item.getPhone().getId(), item -> item));

        cartDisplayInfo.getCartDisplayItems()
                .forEach(cartDisplayItem -> setPropertiesCartItem(cartDisplayItem, items.get(cartDisplayItem.getPhoneId())));
    }

    private void setPropertiesCartItem(CartDisplayItem cartDisplayItem, CartItem cartItem) {
        Phone phone = cartItem.getPhone();
        if (phone != null) {
            cartDisplayItem.setBrand(phone.getBrand());
            cartDisplayItem.setImageUrl(phone.getImageUrl());
            cartDisplayItem.setModel(phone.getModel());

            BigDecimal price = phone.getPrice();
            cartDisplayItem.setPrice(price);

            BigDecimal total = price.multiply(new BigDecimal(cartItem.getQuantity()));
            cartDisplayItem.setTotal(total);
        }
    }
}
