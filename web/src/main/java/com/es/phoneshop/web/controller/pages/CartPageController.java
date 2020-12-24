package com.es.phoneshop.web.controller.pages;

import com.es.core.model.entity.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.validation.QuantityInputWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Resource
    private Validator quantityValidator;

    @Autowired
    private CartService cartService;


    @InitBinder("quantityInputWrapper")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(quantityValidator);
    }

    @GetMapping
    public String getCart(HttpSession httpSession, Model model) {
        model.addAttribute("cart", cartService.getCart(httpSession));
        return "cart";
    }

    @PutMapping
    public String updateCart(@RequestParam("phoneId") List<Long> phoneIds,
                             @RequestParam("quantity") List<String> quantities,
                             Model model,
                             HttpSession httpSession) {
        Cart cart = cartService.getCart(httpSession);

        Map<Long, String> errors = new HashMap<>();
        Map<Long, Long> itemsMap = new HashMap<>();

        for (int i = 0; i < phoneIds.size(); i++) {
            var quantityInputWrapper = new QuantityInputWrapper(quantities.get(i));
            var bindingResult = validate(quantityInputWrapper, quantityValidator);
            if (bindingResult.hasErrors()) {
                errors.put(phoneIds.get(i), bindingResult.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(". ")));
            } else {
                itemsMap.put(phoneIds.get(i), Long.parseLong(quantities.get(i)));
            }
        }
        errors.forEach((aLong, s) -> System.out.println(aLong + ": " + s));
        model.addAttribute("errors",
                cartService.update(cart, itemsMap, errors));
        model.addAttribute("cart", cart);
        return "cart";
    }

    private BindingResult validate(Object target, Validator validator) {
        DataBinder dataBinder = new DataBinder(target);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        return dataBinder.getBindingResult();
    }

    @DeleteMapping("/{phoneId}")
    public String deleteFromCart(@PathVariable String phoneId, HttpSession httpSession) {
        Cart cart = cartService.getCart(httpSession);
        cartService.remove(cart, Long.parseLong(phoneId));
        return "redirect:/cart";
    }
}
