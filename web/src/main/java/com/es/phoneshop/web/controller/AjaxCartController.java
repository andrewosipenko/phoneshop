package com.es.phoneshop.web.controller;

import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartService;
import com.es.core.model.stock.StockService;
import com.es.phoneshop.web.controller.pages.dto.AddCartForm;
import com.es.phoneshop.web.controller.pages.dto.InfoCartResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    public static final String INVALID_INPUT = "Invalid input";
    public static final String ERROR = "Error";
    public static final String ADDED_TO_CART = "Successfully added to cart";
    public static final String SUCCESS = "Success";
    public static final String SHOULD_GREATER_0 = "Should be > 0";
    public static final String NOT_ENOUGH_STOCK = "Not enough stock";
    @Resource
    private CartService cartService;
    @Resource
    private StockService stockService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    InfoCartResponse addPhone(@Valid AddCartForm addCartForm, BindingResult result) {
        String status;
        if (result.hasErrors()) {
            status = ERROR;
        } else {
            status = SUCCESS;
        }
        InfoCartResponse infoCartResponse = createResponseInfo(status, addCartForm, cartService.getCart());
        if (infoCartResponse.getStatus().equals(SUCCESS)) {
            cartService.addPhone(addCartForm.getPhoneId(), addCartForm.getQuantity());
            infoCartResponse.setTotalPrice(cartService.getCart().getTotalCost());
            infoCartResponse.setTotalQuantity(cartService.getCart().getTotalQuantity().intValue());
        }
        return infoCartResponse;
    }

    private InfoCartResponse createResponseInfo(String status, AddCartForm addCartForm, Cart cart) {
        InfoCartResponse infoCartResponse = new InfoCartResponse();
        int phoneInCart;
        try {
            phoneInCart = cartService.getCartItem(addCartForm.getPhoneId()).getQuantity();
        } catch (PhoneNotFindException e) {
            phoneInCart = 0;
        }
        if (status.equals(ERROR)) {
            infoCartResponse.setStatus(ERROR);
            if (addCartForm.getQuantity() != null && addCartForm.getQuantity() < 1) {
                infoCartResponse.setMessage(SHOULD_GREATER_0);
            } else {
                infoCartResponse.setMessage(INVALID_INPUT);
            }
        } else if (stockService.getAvailablePhoneStock(addCartForm.getPhoneId()) -
                phoneInCart < addCartForm.getQuantity()) {
            infoCartResponse.setStatus(ERROR);
            infoCartResponse.setMessage(NOT_ENOUGH_STOCK);
        } else if (status.equals(SUCCESS)) {
            infoCartResponse.setStatus(SUCCESS);
            infoCartResponse.setMessage(ADDED_TO_CART);
            infoCartResponse.setTotalPrice(cart.getTotalCost());
            infoCartResponse.setTotalQuantity(cart.getTotalQuantity().intValue());
        }
        return infoCartResponse;
    }
}
