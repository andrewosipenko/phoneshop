package com.es.facade;

import com.es.core.dto.CartInfoDTO;
import com.es.core.response.AddToCartResponseBody;
import com.es.core.converter.AddingToCartConverter;
import com.es.core.form.AddingToCartForm;
import com.es.core.model.AddingToCartModel;
import com.es.core.model.CartModel;
import com.es.service.CartService;
import com.es.core.exceptions.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class AddingToCartFacade {

    @Resource
    private CartService cartService;

    @Resource
    private AddingToCartConverter addingToCartConverter;

    public ResponseEntity updateCart(CartInfoDTO cartInfo) throws OutOfStockException {
        AddToCartResponseBody ajaxResponseBody = new AddToCartResponseBody();
        AddingToCartForm addingToCartForm = new AddingToCartForm(Long.valueOf(cartInfo.getQuantityToAdd()), cartInfo.getProductId());
        AddingToCartModel addingToCartModel = addingToCartConverter.convert(addingToCartForm);
        CartModel cartModel = cartService.getCart();

        cartService.addPhone(addingToCartModel);

        cartModel.setTotalCost(cartService.getCart().getTotalCost());
        cartModel.setTotalQuantity(cartService.getCart().getTotalQuantity());

        ajaxResponseBody.setQuantityToAdd(BigDecimal.valueOf(cartModel.getTotalQuantity()));
        ajaxResponseBody.setTotalCost(cartModel.getTotalCost());
        ajaxResponseBody.setTotalQuantity(BigDecimal.valueOf(cartModel.getTotalQuantity()));
        return new ResponseEntity<>(ajaxResponseBody, HttpStatus.OK);
    }

    public ResponseEntity updatingInvalidInput(BindingResult bindingResult) {
        AddToCartResponseBody ajaxResponseBody = new AddToCartResponseBody();
        ajaxResponseBody.setMsg(bindingResult.getFieldError("quantityToAdd").getDefaultMessage());
        return new ResponseEntity<>(ajaxResponseBody.getMsg(), HttpStatus.BAD_REQUEST);
    }
}
