package com.es.core.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CartPageDTO {

    @Valid
    @NotNull
    private List<CartInfoDTO> cartInfoDTOList;

    public CartPageDTO() {
        cartInfoDTOList = new ArrayList<>();
    }

    public List<CartInfoDTO> getCartInfoDTOList() {
        return cartInfoDTOList;
    }

    public void setCartInfoDTOList(List<CartInfoDTO> cartInfoDTOS) {
        this.cartInfoDTOList = cartInfoDTOS;
    }
}
