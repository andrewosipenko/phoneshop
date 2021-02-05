package com.es.core.converter;

import com.es.core.dto.CartPageDTO;
import com.es.core.model.CartPageModel;
import com.es.core.populator.impl.CartPageDTOToCartModelPopulator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class CartPageDTOConverter implements Converter<CartPageDTO, CartPageModel> {

    private List<CartPageDTOToCartModelPopulator> populators;

    @Override
    public CartPageModel convert(CartPageDTO cartPageDTO) {
        CartPageModel cartPageModel = new CartPageModel();
        populators.stream().forEach(populator -> populator.populate(cartPageDTO, cartPageModel));
        return cartPageModel;
    }

    public List<CartPageDTOToCartModelPopulator> getPopulators() {
        return populators;
    }

    public void setPopulators(List<CartPageDTOToCartModelPopulator> populators) {
        this.populators = populators;
    }
}
