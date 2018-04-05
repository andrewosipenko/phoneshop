package com.es.core.model.phone;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService{

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> findAll(int offset, int limit, String sortBy) {
        return phoneDao.findAll(offset, limit, sortBy);
    }

    @Override
    public int countAll() {
        return phoneDao.countAll();
    }

    @Override
    public List<Phone> searchByModel(String keyString, int limit, int offset, String sortBy) {
        return phoneDao.searchByModel(keyString, limit, offset, sortBy);
    }

    @Override
    public int countSearchResult(String keyString) {
        return phoneDao.countSearchResult(keyString);
    }
}
