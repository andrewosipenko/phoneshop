package com.es.core.service.orderItem;

import com.es.core.dao.orderItem.OrderItemDao;
import com.es.core.model.order.OrderItem;
import com.es.core.service.stock.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private StockService stockService;

    @Override
    public void save(List<OrderItem> orderItems, Long orderId) {
        orderItems.forEach(orderItem -> {
            orderItemDao.save(orderItem, orderId);
            stockService.addReserved(orderItem.getPhone().getId(), orderItem.getQuantity());
        });
    }
}
