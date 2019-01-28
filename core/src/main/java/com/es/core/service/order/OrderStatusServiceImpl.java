package com.es.core.service.order;

import com.es.core.model.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    @Override
    public List<String> getOrderStatusNamesExcept(OrderStatus orderStatus){
        return Arrays.asList(OrderStatus.values()).
                stream().filter(status -> !status.toString().equals(orderStatus.toString())).
                map(OrderStatus::toString).collect(Collectors.toList());
    }
}
