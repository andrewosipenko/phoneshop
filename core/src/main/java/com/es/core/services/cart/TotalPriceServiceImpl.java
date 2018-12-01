package com.es.core.services.cart;

import com.es.core.dao.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class TotalPriceServiceImpl implements TotalPriceService {
    @Resource
    private Cart cart;
    @Resource
    private PhoneDao phoneDao;

    @Override
    public BigDecimal getTotalPriceOfProducts() {
        BigDecimal totalCartPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            totalCartPrice = totalCartPrice.add(phoneDao.get(cartItem.getPhoneId()).get().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        return totalCartPrice;
    }
}
