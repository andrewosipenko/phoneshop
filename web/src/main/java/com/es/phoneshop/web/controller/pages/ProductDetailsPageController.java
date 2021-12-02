package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    public static final String PHONE = "phone";
    public static final String CART = "cart";
    public static final String PRODUCT_DETAILS = "productDetails";
    public static final String NO_SUCH_PHONE_ERROR = "error/noSuchPhoneErrorPage";

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private CartService cartService;

    @RequestMapping(value = "/{phoneId}", method = RequestMethod.GET)
    public String showProductPage(@PathVariable String phoneId, Model model) {
        try {
            Phone phone = phoneDao.get(Long.parseLong(phoneId)).get();
            model.addAttribute(PHONE, phone);
            model.addAttribute(CART, cartService.getCart());
            return PRODUCT_DETAILS;
        } catch (PhoneNotFindException | NumberFormatException e) {
            return NO_SUCH_PHONE_ERROR;
        }
    }
}
