package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.web.controller.throwable.InternalException;
import com.es.phoneshop.web.controller.util.UpdateCartForm;
import com.es.phoneshop.web.controller.util.UpdateCartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        UpdateCartForm form = new UpdateCartForm();
        form.setUpdateCartItems(getUpdateCartItems());
        model.addAttribute("updateCartForm", form);
        model.addAttribute("phones", getPhones());
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@ModelAttribute("updateCartForm") @Valid UpdateCartForm form, BindingResult result, Model model) {
        List<UpdateCartItem> items = form.getUpdateCartItems();
        model.addAttribute("phones", getPhones());
        Map<Long, Long> updateMap = items.stream()
                .filter(item -> item.getPhoneId() != null && item.getQuantity() != null)
                .collect(Collectors.toMap(UpdateCartItem::getPhoneId, UpdateCartItem::getQuantity));
        try {
            cartService.checkUpdateItems(updateMap);
        } catch (TooBigQuantityException e) {
            handleTooBigQuantities(result, e.getPhoneIds(), items);
        } catch (NoSuchPhoneException | NoStockFoundException e) {
            throw new InternalException();
        }
        if (result.hasFieldErrors())
            return "cart";
        cartService.update(updateMap);
        return "redirect:/cart";
    }

    private void handleTooBigQuantities(BindingResult result, Set<Long> phoneIds, List<UpdateCartItem> items) {
        for (int i = 0; i < items.size(); i++) {
            Long phoneId = items.get(i).getPhoneId();
            if (phoneIds.contains(phoneId))
                result.rejectValue("updateCartItems[" + i + "].quantity", "error.tooBig.quantity");
        }
    }

    private List<Phone> getPhones() {
        return cartService.getCartItems().stream()
                .map(CartItem::getPhone)
                .collect(Collectors.toList());
    }

    private List<UpdateCartItem> getUpdateCartItems() {
        return cartService.getCartItems().stream()
                .map(UpdateCartItem::fromCartItem)
                .collect(Collectors.toList());
    }
}
