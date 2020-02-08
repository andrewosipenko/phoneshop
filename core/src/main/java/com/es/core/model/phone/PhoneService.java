package com.es.core.model.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    @Autowired
    private JdbcPhoneDao phoneDao;

    public Optional<Phone> get(final Long key) {
        return phoneDao.get(key);
    }

    public void save(final Phone phoneToSave) {
        phoneDao.save(phoneToSave);
    }

    public List<Phone> findAll(int offset, int limit) {
        return phoneDao.findAll(offset, limit);
    }

    public void delete(Phone phone) {
        phoneDao.delete(phone);
    }
}
