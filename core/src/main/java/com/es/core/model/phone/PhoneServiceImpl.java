package com.es.core.model.phone;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public Optional<Phone> get(Long key) {
        return phoneDao.get(key);
    }

    @Override
    public List<Phone> findInOrder(String orderBy, int offset, int limit) {
        return phoneDao.findByModelInOrder("%", orderBy, offset, limit);
    }

    @Override
    public List<Phone> findByModelInOrder(String model, String orderBy, int offset, int limit) {
        return phoneDao.findByModelInOrder("%" + model + "%", orderBy, offset, limit);
    }

    @Override
    public long productsCount() {
        return phoneDao.productsCountWithModel("%");
    }

    @Override
    public long productsCountByModel(String model) {
        return phoneDao.productsCountWithModel("%" + model + "%");
    }
}
