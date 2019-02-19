package com.es.core.service.phone;

import com.es.core.dao.color.ColorDao;
import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private ColorDao colorDao;

    @Override
    public List<Phone> getPhonesByPage(int pageId, int phonesOnPage) {
        return null;
    }
}
