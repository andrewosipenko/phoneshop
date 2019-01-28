package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.exceptions.phone.PhoneException;
import com.es.core.form.cart.AddCartForm;
import com.es.core.form.cart.UpdateCartForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.service.phone.PhoneService;
import com.es.core.service.stock.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private Cart cart;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private PriceService priceService;
    @Resource
    private StockService stockService;
    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        if (!phoneDao.contains(phoneId)) {
            throw new PhoneException();
        }
        if (cart.getCartItems().containsKey(phoneId)) {
            Long oldQuantity = cart.getCartItems().get(phoneId);
            cart.getCartItems().put(phoneId, oldQuantity + quantity);
        } else {
            cart.getCartItems().put(phoneId, quantity);
        }
        priceService.recalculateCart(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> item : items.entrySet()) {
            Long phoneId = item.getKey();
            Long quantity = item.getValue();
            if (!phoneDao.contains(phoneId)) {
                throw new PhoneException();
            }
            cart.getCartItems().put(phoneId, quantity);
        }
        priceService.recalculateCart(cart);
    }

    @Override
    public void remove(Long phoneId) {
        if (!cart.getCartItems().containsKey(phoneId)) {
            throw new PhoneException();
        }
        cart.getCartItems().remove(phoneId);
        priceService.recalculateCart(cart);
    }

    @Override
    public UpdateCartForm getUpdateCart(List<Phone> phones, Map<Long, Long> cartItems) {
        UpdateCartForm updateCartForm = new UpdateCartForm();
        List<AddCartForm> cartFormList = new ArrayList<>();
        for (Phone phone : phones) {
            AddCartForm addCartForm = new AddCartForm();
            Long phoneId = phone.getId();
            addCartForm.setPhoneId(phoneId);
            Long quantity = cartItems.get(phoneId);
            addCartForm.setQuantity(quantity);
            cartFormList.add(addCartForm);
        }
        updateCartForm.setCartFormList(cartFormList);
        return updateCartForm;
    }

    @Override
    public Map<Long, Long> getItemsCart(UpdateCartForm updateCartForm) {
        Map<Long, Long> cartItems = new HashMap<>();
        for (AddCartForm addCartForm : updateCartForm.getCartFormList()) {
            cartItems.put(addCartForm.getPhoneId(), addCartForm.getQuantity());
        }
        return cartItems;
    }

    @Override
    public boolean removePhonesOutOfTheStock() {
        boolean isExistPhoneOutOfTheStock = false;
        Cart cart = getCart();
        List<Phone> phones = phoneService.getPhoneListFromCart(cart);
        List<Stock> stocks = stockService.getPhonesStocks(phones);
        for (Stock stock : stocks) {
            Long phoneId = stock.getPhone().getId();
            Long quantity = cart.getItemQuantity(phoneId);
            if (stock.getStock() < quantity){
                remove(phoneId);
                isExistPhoneOutOfTheStock = true;
            }
        }
        return isExistPhoneOutOfTheStock;
    }

    @Override
    public void clearCart() {
        getCart().getCartItems().clear();
        priceService.recalculateCart(getCart());
    }
}
