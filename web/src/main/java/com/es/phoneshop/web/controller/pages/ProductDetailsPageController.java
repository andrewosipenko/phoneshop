package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.bean.cart.CartItemInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Optional;

import static com.es.phoneshop.web.controller.constants.ControllerConstants.ProductDetailsConstants.PHONE;
import static com.es.phoneshop.web.controller.constants.ControllerConstants.ProductDetailsConstants.CART_ITEM;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET, value = "/{phoneId}")
    public String showProductDetails(@PathVariable Long phoneId, Model model) {
        Optional<Phone> phone = phoneDao.get(phoneId);
            model.addAttribute(PHONE, phone.orElse(null));
            model.addAttribute(CART_ITEM, new CartItemInfo());
        return "productDetails";
    }
}
