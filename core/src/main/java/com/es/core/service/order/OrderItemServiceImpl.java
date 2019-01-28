package com.es.core.service.order;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.exceptions.phone.PhoneException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<OrderItem> getOrderItemList(Cart cart) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : cart.getCartItems().entrySet()) {
            Long phoneId = entry.getKey();
            Long quantity = entry.getValue();
            Phone phone = phoneDao.get(phoneId).orElseThrow(PhoneException::new);
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(phone);
            orderItem.setQuantity(quantity);
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
