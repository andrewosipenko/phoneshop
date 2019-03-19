package com.es.core.service.stock;

import com.es.core.dao.stock.StockDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockServiceImpl implements StockService {
    @Resource
    private StockDao stockDao;

    @Override
    public long findPhoneStock(Long key) {
        return stockDao.findPhoneQuantity(key);
    }
}
