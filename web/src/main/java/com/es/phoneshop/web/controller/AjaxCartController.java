package com.es.phoneshop.web.controller;

import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.cart.CartService;
import com.es.core.model.stock.StockService;
import com.es.phoneshop.web.controller.dto.AddCartRequest;
import com.es.phoneshop.web.controller.dto.InfoCartResponse;
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

    public static final String ERROR = "Error";
    public static final String ADDED_TO_CART = "Successfully added to cart";
    public static final String SUCCESS = "Success";
    public static final String NOT_ENOUGH_STOCK = "Not enough stock";
    public static final String QUANTITY = "quantity";
    @Resource
    private CartService cartService;
    @Resource
    private StockService stockService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    InfoCartResponse addPhone(@Valid AddCartRequest addCartForm, BindingResult result) {
        InfoCartResponse response = new InfoCartResponse();
        if (result.hasErrors()) {
            response.setStatus(ERROR);
            response.setMessage(result.getFieldError(QUANTITY).getDefaultMessage());
        } else {
            if (isInStock(addCartForm)) {
                response.setStatus(ERROR);
                response.setMessage(NOT_ENOUGH_STOCK);
                return response;
            }
            response.setStatus(SUCCESS);
            response.setMessage(ADDED_TO_CART);
            cartService.addPhone(addCartForm.getPhoneId(), addCartForm.getQuantity());
            response.setTotalPrice(cartService.getCart().getTotalCost());
            response.setTotalQuantity(cartService.getCart().getTotalQuantity().intValue());
        }
        return response;
    }

    private boolean isInStock(AddCartRequest addCartForm) {
        return stockService.getAvailablePhoneStock(addCartForm.getPhoneId()) -
                getPhoneInCart(addCartForm) < addCartForm.getQuantity();
    }

    private int getPhoneInCart(AddCartRequest addCartForm) {
        int phoneInCart;
        try {
            phoneInCart = cartService.getCartItem(addCartForm.getPhoneId()).getQuantity();
        } catch (PhoneNotFindException e) {
            phoneInCart = 0;
        }
        return phoneInCart;
    }
}
