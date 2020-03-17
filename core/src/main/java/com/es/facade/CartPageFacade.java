package com.es.facade;

import com.es.core.dto.CartPageDTO;
import com.es.core.converter.CartPageDTOConverter;
import com.es.core.model.CartModel;
import com.es.core.model.CartPageModel;
import com.es.service.HttpSessionCartService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

public class CartPageFacade {

    @Resource
    private HttpSession httpSession;

    @Resource
    private HttpSessionCartService cartService;

    @Resource
    private CartPageDTOConverter cartDtoConverter;

    public String getCart(Model model) {
        CartModel cart = (CartModel) httpSession.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "cart";
    }

    public void deleteCartItemFromCart(@RequestParam(name = "idToDelete", required = false) Long idToDelete) {
        cartService.remove(idToDelete);
    }

    public String updateCart(CartPageDTO cartPageDTO) {
        CartModel cartModel = cartService.getCart();
        CartPageModel cartPageModel = new CartPageModel();
        cartPageModel = cartDtoConverter.convert(cartPageDTO);
        cartService.update(cartPageModel.getCartInfoModels(), cartModel.getCartItems());
        return "redirect:/cart";
    }
}
