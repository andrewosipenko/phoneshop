package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.ProductDao;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private CartService cartService;

    @Resource
    private ProductDao productDao;

    @RequestMapping(value = "/{phoneId}")
    public String details(@PathVariable String phoneId, Model model) {
        Phone phone = productDao.loadPhoneById(Long.parseLong(phoneId));
        model.addAttribute("phone", phone);
        cartService.updateTotals();
        model.addAttribute("cart", cartService.getCart());
        return "productDetails";
    }
}
