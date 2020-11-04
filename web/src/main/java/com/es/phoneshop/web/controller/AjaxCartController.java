package com.es.phoneshop.web.controller;

import com.es.core.model.entity.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.dto.AddPhoneRequestDTO;
import com.es.phoneshop.web.controller.dto.AddPhoneResponseDTO;
import com.es.phoneshop.web.controller.dto.MiniCartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Autowired
    @Qualifier("addPhoneDtoValidator")
    private Validator addPhoneDtoValidator;

    @Autowired
    private CartService<HttpSession> cartService;

    @InitBinder("addPhoneRequestDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(addPhoneDtoValidator);
    }

    @GetMapping
    public MiniCartDTO getMiniCart(HttpSession httpSession) {
        Cart cart = cartService.getCart(httpSession);
        MiniCartDTO miniCartDTO = new MiniCartDTO();
        miniCartDTO.setTotalQuantity(cart.getTotalQuantity());
        miniCartDTO.setTotalCost(cart.getTotalCost());
        return miniCartDTO;
    }

    @PostMapping
    public AddPhoneResponseDTO addPhone(AddPhoneRequestDTO addPhoneRequestDTO,
                                        HttpSession httpSession,
                                        BindingResult bindingResult) {

        Cart cart = cartService.getCart(httpSession);
        addPhoneDtoValidator.validate(addPhoneRequestDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return createAddPhoneResponseDTO(false, cart, bindingResult);
        }
        cartService.addPhone(cart, addPhoneRequestDTO.getPhoneId(), Long.valueOf(addPhoneRequestDTO.getQuantity()));
        return createAddPhoneResponseDTO(true, cart, bindingResult);
    }

    private AddPhoneResponseDTO createAddPhoneResponseDTO(boolean isSuccessful, Cart cart, BindingResult bindingResult) {
        AddPhoneResponseDTO responseDTO = new AddPhoneResponseDTO();
        MiniCartDTO miniCartDTO = new MiniCartDTO();
        String message;
        miniCartDTO.setTotalCost(cart.getTotalCost());
        miniCartDTO.setTotalQuantity(cart.getTotalQuantity());
        responseDTO.setMiniCart(miniCartDTO);
        responseDTO.setSuccessStatus(isSuccessful);
        if (isSuccessful) {
            message = "Successfully added to cart";
        } else {
            message = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        }
        responseDTO.setMessage(message);
        return responseDTO;
    }
}
