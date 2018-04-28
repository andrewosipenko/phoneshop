package com.es.phoneshop.core.stock.service;

import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.dao.StockDao;
import com.es.phoneshop.core.stock.model.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    @Resource
    private StockDao stockDao;

    @Override
    public Optional<Stock> getStock(Phone phone) {
        return stockDao.get(phone);
    }

    @Override
    public void update(Stock stock) {
        stockDao.save(stock);
    }
}
