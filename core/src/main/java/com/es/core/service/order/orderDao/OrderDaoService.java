package com.es.core.service.order.orderDao;

import com.es.core.dao.SqlQueryConstants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class OrderDaoService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public String generateOrderKey(){
        String orderKey;
        do {
            orderKey = UUID.randomUUID().toString().replaceAll("-", "");
        }while (isUsed(orderKey));
        return orderKey;
    }

    private boolean isUsed(String orderKey){
        return jdbcTemplate.queryForObject(
                SqlQueryConstants.OrderDao.COUNT_ORDER_KEY + "\'" + orderKey + "\'", Long.class) != 0;
    }
}
