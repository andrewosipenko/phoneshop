package com.es.facade;

import com.es.core.dto.CartPageDTO;
import com.es.core.converter.CartPageDTOConverter;
import com.es.core.model.CartModel;
import com.es.core.model.CartPageModel;
import com.es.service.HttpSessionCartService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

public class CartPageFacade {

    @Resource
    private HttpSession httpSession;

    @Resource
    private HttpSessionCartService cartService;

    @Resource
    private CartPageDTOConverter cartDtoConverter;

    public CartModel getCart() {
        return (CartModel) httpSession.getAttribute("cart");
    }

    public void deleteCartItemFromCart(Long idToDelete) {
        cartService.remove(idToDelete);
    }

    public CartModel updateCart(CartPageDTO cartPageDTO) {
        CartModel cartModel = cartService.getCart();
        CartPageModel cartPageModel = cartDtoConverter.convert(cartPageDTO);
        cartService.update(cartPageModel.getCartInfoModels(), cartModel.getCartItems());
        return cartModel;
    }
}
