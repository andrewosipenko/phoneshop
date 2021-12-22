package com.es.phoneshop.web.controller;

import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.cart.Cart;
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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    public static final String ERROR = "Error";
    public static final String ADDED_TO_CART = "Successfully added to cart";
    public static final String SUCCESS = "Success";
    public static final String NOT_ENOUGH_STOCK = "Not enough stock";
    public static final String QUANTITY = "quantity";
    public static final String INVALID_INPUT = "invalid input";
    @Resource
    private CartService cartService;
    @Resource
    private StockService stockService;
    @Resource
    private HttpSession session;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    InfoCartResponse addPhone(@Valid AddCartRequest addCartForm, BindingResult result) {
        InfoCartResponse response = new InfoCartResponse();
        if (result.hasErrors()) {
            response.setStatus(ERROR);
            if(result.getFieldError(QUANTITY).getDefaultMessage().length() > 30){
                response.setMessage(INVALID_INPUT);
            } else {
                response.setMessage(result.getFieldError(QUANTITY).getDefaultMessage());
            }
        } else {
            Cart cart = cartService.getCart(session);
            if (isInStock(addCartForm, cart)) {
                response.setStatus(ERROR);
                response.setMessage(NOT_ENOUGH_STOCK);
                return response;
            }
            response.setStatus(SUCCESS);
            response.setMessage(ADDED_TO_CART);
            cartService.addPhone(addCartForm.getPhoneId(), addCartForm.getQuantity(), cart);
            response.setTotalPrice(cart.getTotalCost());
            response.setTotalQuantity(cart.getTotalQuantity().intValue());
        }
        return response;
    }

    private boolean isInStock(AddCartRequest addCartForm, Cart cart) {
        return stockService.getAvailablePhoneStock(addCartForm.getPhoneId()) -
                getPhoneInCart(addCartForm, cart) < addCartForm.getQuantity();
    }

    private int getPhoneInCart(AddCartRequest addCartForm, Cart cart) {
        int phoneInCart;
        try {
            phoneInCart = cartService.getCartItem(addCartForm.getPhoneId(), cart).getQuantity();
        } catch (PhoneNotFindException e) {
            phoneInCart = 0;
        }
        return phoneInCart;
    }
}
