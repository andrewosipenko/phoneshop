package com.es.core.service.phone;

import com.es.core.model.cart.Cart;
import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Resource
    protected PhoneDao phoneDao;

    @Override
    public Optional<Phone> getPhone(Long key) {
        return phoneDao.get(key);
    }

    @Override
    public void save(Phone phone) {
        phoneDao.save(phone);
    }

    @Override
    public void delete(Phone phone) {
        phoneDao.delete(phone);
    }

    @Override
    public List<Phone> getPhoneListFromCart(Cart cart){
        List<Phone> phones = new ArrayList<>();
        for (Long phoneId : cart.getCartItems().keySet()) {
            phones.add(phoneDao.get(phoneId).get());
        }
        return phones;
    }

    @Override
    public List<Phone> getPhoneListFromOrder(Order order){
        List<Phone> phones = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            phones.add(orderItem.getPhone());
        }
        return phones;
    }
}
