package com.es.core.populator.impl;

import com.es.core.dto.CartInfoDTO;
import com.es.core.dto.CartPageDTO;
import com.es.core.model.CartPageModel;
import com.es.core.populator.interfaces.Populator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartPageDTOToCartModelPopulator implements Populator<CartPageDTO, CartPageModel> {

    @Override
    public void populate(CartPageDTO pageDTO, CartPageModel model) {
        model.setCartInfoModels(getMapOfPhoneQuantities(pageDTO.getCartInfoDTOList()));
    }

    private Map<Long, String> getMapOfPhoneQuantities(List<CartInfoDTO> cartItems) {
        return cartItems.stream()
                        .collect(Collectors.toMap(CartInfoDTO::getProductId, CartInfoDTO::getQuantityToAdd));
    }
}
