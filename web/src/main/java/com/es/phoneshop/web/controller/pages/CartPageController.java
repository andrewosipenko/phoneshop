package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.CartItem;
import com.es.core.model.cart.CartService;
import com.es.core.model.stock.StockService;
import com.es.phoneshop.web.controller.dto.QuantityForm;
import com.es.phoneshop.web.controller.dto.UpdateCartRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    public static final String CART = "cart";
    public static final String UPDATE_CART_REQUEST = "updateCartRequest";
    public static final String PRODUCT_CART = "productCart";
    public static final String REDIRECT_CART = "redirect:/cart";
    public static final String DELETE_CART_ITEM_URL = "/deleteCartItem";
    public static final String UPDATE_URL = "/update";
    public static final String ERROR = "error";
    public static final String QUANTITY_FORM_MAP_QUANTITY = "quantityFormMap[%d].quantity";
    public static final String OUT_OF_STOCK = "Out of stock";
    public static final String INVALID_INPUT = "invalid input";

    @Resource
    private HttpSession session;

    @Resource
    private CartService cartService;

    @Resource
    private StockService stockService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute(CART, cartService.getCart(session));
        model.addAttribute(UPDATE_CART_REQUEST, createUpdateCartRequest());
        return PRODUCT_CART;
    }

    @RequestMapping(value = DELETE_CART_ITEM_URL, method = RequestMethod.GET)
    public String deleteItem(long phoneId) {
        cartService.remove(phoneId, cartService.getCart(session));
        return REDIRECT_CART;
    }

    @RequestMapping(value = UPDATE_URL, method = RequestMethod.POST)
    public String updateCart(@Valid UpdateCartRequest updateCartRequest, BindingResult result, Model model) {
        Map<Long, Long> updateMap = new HashMap<>();
        if (checkError(result, model)) return PRODUCT_CART;
        updateCartRequest.getQuantityFormMap().forEach((id, quantityForm) -> {
            try {
                if (stockService.getAvailablePhoneStock(id) < quantityForm.getQuantity()) {
                    throw new OutOfStockException(id.toString());
                }
                updateMap.put(id, quantityForm.getQuantity());
            } catch (OutOfStockException e){
                result.addError(new FieldError(ERROR, String.format(QUANTITY_FORM_MAP_QUANTITY, id), OUT_OF_STOCK));
            }
        });
        if (checkError(result, model)) return PRODUCT_CART;
        cartService.update(updateMap, cartService.getCart(session));
        return REDIRECT_CART;
    }

    private boolean checkError(BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> {
                if (fieldError.getDefaultMessage().length() > 30){
                    result.addError(new FieldError(ERROR, fieldError.getField(), INVALID_INPUT));

                }
            });
            model.addAttribute(CART, cartService.getCart(session));
            return true;
        }
        return false;
    }

    private UpdateCartRequest createUpdateCartRequest() {
        List<CartItem> cartItemList = cartService.getCart(session).getCartItems();
        Map<Long, QuantityForm> quantityFormMap = new HashMap<>();
        cartItemList.forEach(cartItem ->
                quantityFormMap.put(cartItem.getPhone().getId(), new QuantityForm(cartItem.getQuantity().longValue())));
        UpdateCartRequest updateCartRequest = new UpdateCartRequest();
        updateCartRequest.setQuantityFormMap(quantityFormMap);
        return updateCartRequest;
    }
}
