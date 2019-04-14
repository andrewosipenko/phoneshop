package com.es.core.service.stock;

import com.es.core.dao.stock.StockDao;
import com.es.core.model.stock.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockServiceImpl implements StockService {
    @Resource
    private StockDao stockDao;

    @Override
    public Stock findPhoneStock(Long id) {
        return stockDao.findPhoneStock(id);
    }

    @Override
    public void addReserved(Long id, Long reserved) {
        Stock stock = findPhoneStock(id);
        stockDao.updateStock(id, stock.getStock() - reserved, stock.getReserved() + reserved);
    }

    @Override
    public void replaceReservedToStock(Long id, Long reserved) {
        Stock stock = findPhoneStock(id);
        stockDao.updateStock(id, stock.getStock() + reserved, stock.getReserved() - reserved);
    }

    @Override
    public void deleteReserved(Long id, Long reserved) {
        Stock stock = findPhoneStock(id);
        stockDao.updateStock(id, stock.getStock(), stock.getReserved() - reserved);
    }
}
