package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.bean.cart.CartFieldError;
import com.es.phoneshop.web.bean.cart.CartItem;
import com.es.phoneshop.web.bean.cart.CartPhoneInfo;
import com.es.phoneshop.web.bean.cart.CartUpdateInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    private final static String CART_FIELD_ERROR_MAP_ATTRIBUTE = "cartFiledErrors";

    private final static String ERROR_MESSAGE_WRONG_FORMAT = "Wrong format";

    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        return getCartPage(model);
    }

    @PutMapping(params = "update")
    public String updateCart(@Valid CartUpdateInfo cartUpdateInfos, BindingResult bindingResult, Model model) throws PhoneNotFoundException {
        if (!bindingResult.hasErrors()) {
            Map<Long, Long> mapForUpdate = cartUpdateInfos.getCartPhoneInfos().stream()
                    .collect(Collectors.toMap(CartPhoneInfo::getPhoneId, CartPhoneInfo::getQuantity));
            cartService.update(mapForUpdate);
        } else {
            model.addAttribute(CART_FIELD_ERROR_MAP_ATTRIBUTE, getErrorMessageMap(bindingResult));
        }

        return getCartPage(model);
    }

    @PostMapping(params = "remove")
    public String deleteProductFormCart(@ModelAttribute("phoneId") Long phoneId, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            cartService.remove(phoneId);
        }
        return getCartPage(model);
    }

    private String getCartPage(Model model) {
        Cart cart = cartService.getCart();
        Map<Phone, Long> items = cart.getItems();
        List<Phone> phonesList = new ArrayList<>(items.keySet());
        List<CartItem> cartItems = phonesList.stream()
                .map(phone -> new CartItem(phone, items.get(phone)))
                .collect(Collectors.toList());
        model.addAttribute(CART_ITEM_LIST_ATTRIBUTE, cartItems);
        return CART_PAGE_NAME;
    }

    private Map<String, CartFieldError> getErrorMessageMap(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().filter(o -> o instanceof FieldError).map(o -> (FieldError) o)
                .collect(Collectors.toMap(FieldError::getField, f -> createCartFieldError(f, ERROR_MESSAGE_WRONG_FORMAT)));
    }

    private CartFieldError createCartFieldError(FieldError field, String message) {
        return new CartFieldError(field.getField(), field.getRejectedValue().toString(), message);
    }
}
