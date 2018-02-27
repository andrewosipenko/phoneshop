package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.phoneshop.web.util.CartUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails/{id}")
public class ProductDetailsPageController {
    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    private static final String ATTRIBUTE_PHONE = "phone";
    private static final String ATTRIBUTE_CART_STATUS = "cartStatus";

    @GetMapping
    public String showProductDetails(@PathVariable long id,
                                     Model model) {
        CartUtils.addCartStatusInModel(cartService, model, ATTRIBUTE_CART_STATUS);

        Optional<Phone> phone = phoneService.get(id);
        if (phone.isPresent()) {
            model.addAttribute(ATTRIBUTE_PHONE, phone.get());
            return "productDetails";
        } else {
            return "/page404.jsp";
        }
    }
}
