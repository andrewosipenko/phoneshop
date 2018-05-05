package com.es.core.service.order.orderDao;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderDaoService {
    public String generateOrderKey(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
