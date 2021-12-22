package com.es.core.model.stock;

import com.es.core.exception.OutOfStockException;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.order.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultStockService implements StockService {
    @Resource
    private StockDao stockDao;

    @Override
    public int getAvailablePhoneStock(Long id) throws PhoneNotFindException {
        return stockDao.getStock(id) - stockDao.getReserved(id);
    }

    @Override
    public List<Long> updateStocks(Order order) throws OutOfStockException {
        List<Long> errorsPhoneIds = new ArrayList<>();
        order.getOrderItems()
                .forEach(orderItem -> {
                    if(!stockDao.setReserved(orderItem.getPhoneId(), orderItem.getQuantity())){
                        errorsPhoneIds.add(orderItem.getPhoneId());
                    }
                });
        return errorsPhoneIds;
    }
}
