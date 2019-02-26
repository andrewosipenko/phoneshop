package com.es.core.service.phone;

import com.es.core.dao.color.ColorDao;
import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private ColorDao colorDao;

    @Override
    public Optional<Phone> get(Long key) {
        return phoneDao.get(key);
    }

    @Override
    public List<Phone> findActivePhonesByPage(int offset, int limit) {
        return phoneDao.findActivePhonesByPage(offset, limit);
    }

    @Override
    public List<Phone> findPhonesLikeSearchText(int offset, int limit, String searchText) {
        return phoneDao.findPhonesLikeSearchText(offset, limit, searchText);
    }

    @Override
    public List<Phone> sortPhones(int offset, int limit, String sort, String order) {
        return phoneDao.sortPhones(offset, limit, sort, order);
    }

    @Override
    public List<Phone> sortPhonesLikeSearchText(int offset, int limit, String sort, String order, String searchText) {
        return phoneDao.sortPhonesLikeSearchText(offset, limit, sort, order, searchText);
    }

    @Override
    public int findPageCount(int pageSize) {
        return phoneDao.findPageCount() / pageSize;
    }

    @Override
    public int findPageCountWithSearchText(int pageSize, String searchText) {
        return phoneDao.findPageCountWithSearchText(searchText) / pageSize;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Phone phone) {
        phone = phoneDao.save(phone);
        colorDao.savePhoneColors(phone);
    }
}
