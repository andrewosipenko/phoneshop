package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.bean.cart.CartItemInfo;
import com.es.phoneshop.web.controller.constants.ProductConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET, value = "/{phoneId}")
    public String showProductDetails(@PathVariable Long phoneId, Model model) {
        Optional<Phone> phone = phoneDao.get(phoneId);
            model.addAttribute("phone", phone.orElse(null));
            model.addAttribute(ProductConstants.CART_ITEM, new CartItemInfo());
        return "productDetails";
    }
}
