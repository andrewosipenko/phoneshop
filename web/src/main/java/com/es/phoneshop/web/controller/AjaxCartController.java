package com.es.phoneshop.web.controller;

import com.es.phoneshop.core.cart.CartService;
import com.es.phoneshop.core.cart.CartStatus;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.web.controller.throwable.IncorrectFormFormatException;
import com.es.phoneshop.web.controller.throwable.ZeroQuantityException;
import com.es.phoneshop.web.controller.util.AddToCartForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<CartStatus> getCartStatus() {
        return new ResponseEntity<>(cartService.getCart().getStatus(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<CartStatus> addPhone(@Valid AddToCartForm form, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            for (FieldError error : bindingResult.getFieldErrors())
                if (error.getField().equals("quantity") && Objects.equals(error.getRejectedValue(), 0L))
                    throw new ZeroQuantityException();
            throw new IncorrectFormFormatException();
        }
        cartService.addPhone(form.getPhoneId(), form.getQuantity());
        return new ResponseEntity<>(cartService.getCart().getStatus(), HttpStatus.OK);
    }

    @ExceptionHandler(ZeroQuantityException.class)
    private @ResponseBody ResponseEntity<String> handleZeroQuantity() {
        return new ResponseEntity<>("Quantity must be positive integer value", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectFormFormatException.class)
    private @ResponseBody ResponseEntity<String> handleIncorrectFormFormat() {
        return new ResponseEntity<>("Incorrect format", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooBigQuantityException.class)
    private @ResponseBody ResponseEntity<String> handleTooBigQuantity() {
        return new ResponseEntity<>("Quantity value is too big", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchPhoneException.class)
    private @ResponseBody ResponseEntity<String> handleNoSuchPhone() {
        return new ResponseEntity<>("No such phone", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoStockFoundException.class)
    private @ResponseBody ResponseEntity<String> handleNoStockFound() {
        return new ResponseEntity<>("No stock info found", HttpStatus.NOT_FOUND);
    }
}
