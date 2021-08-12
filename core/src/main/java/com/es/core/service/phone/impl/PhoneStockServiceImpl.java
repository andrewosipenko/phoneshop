package com.es.core.service.phone.impl;

import com.es.core.dao.stockDao.StockDao;
import com.es.core.service.phone.PhoneStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PhoneStockServiceImpl implements PhoneStockService {

    @Resource
    private StockDao stockDao;

    @Override
    public boolean hasEnoughStock(Long phoneId, Long quantity) {
        return quantity <= stockDao.getPhoneStock(phoneId);
    }
}
