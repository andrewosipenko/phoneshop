package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @Autowired
    private HttpSession httpSession;

    private static final String ATTRIBUTE_COUNT_ITEMS = "countItems";

    private static final String ATTRIBUTE_PRICE = "price";

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String addPhone(@RequestParam Long phoneId,
                                         @RequestParam Long quantity) {
        cartService.addPhone(phoneId, quantity);
        return String.format("My cart: %s items %s$",
                             httpSession.getAttribute(ATTRIBUTE_COUNT_ITEMS),
                             httpSession.getAttribute(ATTRIBUTE_PRICE));
    }

}
