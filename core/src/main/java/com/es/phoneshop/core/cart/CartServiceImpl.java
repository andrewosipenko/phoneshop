package com.es.phoneshop.core.cart;

import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.core.phone.dao.PhoneDao;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.dao.StockDao;
import com.es.phoneshop.core.stock.model.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private StockDao stockDao;
    @Resource
    private Cart cart;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws NoSuchPhoneException, NoStockFoundException, TooBigQuantityException {
        Phone phone = phoneDao.get(phoneId).orElseThrow(NoSuchPhoneException::new);
        Stock stock = stockDao.get(phone).orElseThrow(NoStockFoundException::new);
        if (stock.getStock() < quantity)
            throw new TooBigQuantityException();
        cart.addItem(phone, quantity);
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}
