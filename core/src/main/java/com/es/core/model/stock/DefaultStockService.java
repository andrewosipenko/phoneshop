package com.es.core.model.stock;

import com.es.core.exception.PhoneNotFindException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultStockService implements StockService {
    @Resource
    private StockDao stockDao;

    @Override
    public int getAvailablePhoneStock(Long id) throws PhoneNotFindException {
        return stockDao.getStock(id) - stockDao.getReserved(id);
    }
}
