package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.phoneshop.web.util.CartUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(value = "/productDetails/{id}")
public class ProductDetailsPageController {

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    private static final String ATTRIBUTE_PHONE = "phone";
    private static final String ATTRIBUTE_CART_STATUS = "cartStatus";
    private static final String ATTRIBUTE_COLORS = "colors";

    @GetMapping
    public String showProductDetails(@PathVariable long id,
                                     @RequestParam(value = "color") String colorCode,
                                     Model model) {
        CartUtils.addCartStatusInModel(cartService, model, ATTRIBUTE_CART_STATUS);

        Optional<Phone> phone = phoneService.getByIdAndColor(id, colorCode);
        if (phone.isPresent()) {
            Set<Color> colors = phoneService.get(id).get().getColors();
            model.addAttribute(ATTRIBUTE_PHONE, phone.get());
            model.addAttribute(ATTRIBUTE_COLORS, colors);
            return "productDetails";
        } else {
            return "/page404.jsp";
        }
    }
}
