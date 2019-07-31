package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.ProductDao;
import com.es.core.model.phone.Phone;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Resource
    private CartService cartService;

    @Resource
    private ProductDao productDao;

    @RequestMapping(method = RequestMethod.GET)
    public String cartPage(Model model) {
        cartService.updateTotals();
        Map<Phone, Long> phonesAndCount = new HashMap<>();
        for (Map.Entry<Long, Long> entry : cartService.getCart().getProducts().entrySet()) {
            phonesAndCount.put(productDao.loadPhoneById(entry.getKey()), entry.getValue());
        }
        model.addAttribute("phonesAndCount", phonesAndCount);
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public void updateCart(HttpRequest httpRequest) {
        cartService.update(null);
    }
}
