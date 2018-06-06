package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.beans.CartFormData;
import com.es.phoneshop.web.controller.beans.QuantityForm;
import com.es.phoneshop.web.controller.beans.UpdateCartForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;


    @GetMapping
    public String getCart(Model model) {
        Map<Long, CartFormData> formData = cartService.getCart()
                .getProducts().values().stream().collect(Collectors.toMap(
                        (e)->e.getPhone().getId(),
                        (e)->{
                            CartFormData data = new CartFormData();
                            data.setPhoneId(e.getPhone().getId());
                            data.setQuantity(e.getQuantity().toString());
                            data.setToDelete(false);
                            return data;
                        }));
        UpdateCartForm updateCartForm = new UpdateCartForm();
        updateCartForm.setQuantities(formData);

        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("updateCartForm", updateCartForm);
        addCartInfo(model);
        return "cartPage";
    }

    @PutMapping
    public String updateCart(
            @ModelAttribute @Valid UpdateCartForm updateCartForm,
            BindingResult bindingResult,
            Model model)  {
        if(bindingResult.hasErrors()){
            Map<String, List<String>> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .distinct()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            (field)-> bindingResult.getFieldErrors(field).stream()
                                    .map(FieldError::getDefaultMessage)
                                    .collect(Collectors.toList())
                    ));
            model.addAttribute("cart", cartService.getCart());
            model.addAttribute("updateCartForm", updateCartForm);
            model.addAttribute("errors", errors);
            addCartInfo(model);
            return "cartPage";
        } else {
            Map<Long, Long> updates = updateCartForm.getQuantities().values()
                    .stream().collect(
                            Collectors.toMap(
                                    QuantityForm::getPhoneId,
                                    (f) -> f.isToDelete()? 0L : Long.valueOf(f.getQuantity())
                            ));
            cartService.update(updates);
            return "redirect:cart";
        }
    }


    private void addCartInfo(Model model){
        model.addAttribute("cartSubTotal", cartService.getCartSubTotal());
        model.addAttribute("cartQuantity", cartService.getProductsQuantity());
    }
}
