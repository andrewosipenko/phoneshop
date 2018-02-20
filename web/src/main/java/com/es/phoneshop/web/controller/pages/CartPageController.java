package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.bean.CartItem;
import com.es.phoneshop.web.controller.ControllerConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        Map<Long,Long> items = cart.getItems();
        List<Long> idList = new ArrayList<>(items.keySet());
        List<Phone> phonesList = phoneDao.getPhonesByIdList(idList);
        List<CartItem> cartItems = phonesList.stream()
                .map(phone -> new CartItem(phone,items.get(phone.getId())))
                .collect(Collectors.toList());
        model.addAttribute(ControllerConstants.CART_ITEM_LIST_ATTRIBUTE,cartItems);
        return ControllerConstants.CART_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.PUT, params = "update")
    public String updateCart() throws PhoneNotFoundException{
        return "redirect:cart";
    }

    @RequestMapping(method = RequestMethod.POST, params = "remove")
    public String deleteProductFormCart(@RequestParam Long phoneId) throws PhoneNotFoundException{
        cartService.remove(phoneId);
        return "redirect:cart";
    }
}
