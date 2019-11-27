package com.es.core.service.order.orderItem;

import com.es.core.cart.Cart;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.exception.NoSuchPhoneException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<OrderItem> getOrderItemList(Cart cart){
        List<OrderItem> orderItems = new ArrayList<>();
        for(Map.Entry<Long, Long> entry : cart.getItems().entrySet()){
            Long phoneId = entry.getKey();
            Long quantity = entry.getValue();
            Phone phone = phoneDao.get(phoneId).orElseThrow(NoSuchPhoneException::new);

            orderItems.add(createOrderItem(phone, quantity));
        }
        return orderItems;
    }

    private OrderItem createOrderItem(Phone phone, Long quantity){
        OrderItem orderItem = new OrderItem();
        orderItem.setPhone(phone);
        orderItem.setQuantity(quantity);
        return orderItem;
    }
}
