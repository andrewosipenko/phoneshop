package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class PriceServiceImpl  implements PriceService {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public void recalculateCart(Cart cart) {
        BigDecimal newSubtotal = BigDecimal.ZERO;
        Long newAmount = 0L;
        for (Map.Entry<Long, Long> item : cart.getCartItems().entrySet()) {
            Long phoneId = item.getKey();
            Long quantity = item.getValue();
            Phone phone = phoneDao.get(phoneId).get();
            newSubtotal = newSubtotal.add(phone.getPrice().multiply(BigDecimal.valueOf(quantity)));
            newAmount += quantity;
        }
        cart.setSubtotal(newSubtotal);
        cart.setCartAmount(newAmount);
    }
}
