package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public @ResponseBody String addPhone(@Valid CartInfo cartInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Wrong format";
        } else {
            cartService.addPhone(cartInfo.getPhoneId(), cartInfo.getQuantity());
            return String.format("My cart: %s items %s$",
                                 httpSession.getAttribute(ATTRIBUTE_COUNT_ITEMS),
                                 httpSession.getAttribute(ATTRIBUTE_PRICE));
        }
    }


    private static class CartInfo {

        private Long phoneId;

        @Range(min = 1L)
        private long quantity;

        public Long getPhoneId() {
            return phoneId;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setPhoneId(Long phoneId) {
            this.phoneId = phoneId;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

    }

}
