package com.es.core.service.phone;

import com.es.core.cart.Cart;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.exception.NoSuchPhoneException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneService {
    @Resource
    protected PhoneDao phoneDao;

    public List<Phone> getPhonesFromCart(Cart cart){
        List<Phone> phones = new ArrayList<>();
        for(Long phoneId : cart.getItems().keySet()){
            phones.add(phoneDao.get(phoneId).get());
        }
        return phones;
    }

    public List<Phone> getPhonesFromOrder(Order order){
        List<Phone> phones = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            phones.add(orderItem.getPhone());
        }
        return phones;
    }
}