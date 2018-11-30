package com.es.core.services.phone;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> getPhonesWithPositiveStock(int offset, int limit) {
        return phoneDao.findAllWithPositiveStock(offset, limit);
    }

    @Override
    public List<Phone> getPhonesByKeyword(String keyword) {
        return phoneDao.findAllByKeyword(keyword);
    }

    @Override
    public Long getTotalAmountOfPhonesWithPositiveStock() {
        return phoneDao.getTotalAmountOfAvailablePhones();
    }
}
