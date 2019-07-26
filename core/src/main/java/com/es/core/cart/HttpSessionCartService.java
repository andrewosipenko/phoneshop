package com.es.core.cart;

import com.es.core.model.ProductDao;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private ProductDao productDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        cart.getProducts().put(phoneId, quantity);
    }

    @Override
    public void update(Map<Long, Long> items) {
        cart.setProducts(items);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getProducts().remove(phoneId);
    }

    @Override
    public void calculateTotalPrice() {
        long totalPrice = 0;
        for (Map.Entry<Long, Long> entry : cart.getProducts().entrySet()) {
            totalPrice += Double.parseDouble(productDao.loadPhoneById(entry.getKey()).getPrice().toString()) * entry.getValue();
        }
        cart.setTotalPrice(totalPrice);
    }

    @Override
    public void calculateTotalCount() {
        cart.setTotalCount(cart.getProducts().size());
    }

    @Override
    public void updateTotals() {
        calculateTotalCount();
        calculateTotalPrice();
    }

    @Override
    public void delete(long phoneId) {
        cart.getProducts().remove(phoneId);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
