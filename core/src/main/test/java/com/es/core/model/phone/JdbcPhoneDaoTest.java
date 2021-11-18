package com.es.core.dao.productDao;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcProductDaoIntTest {
    @Resource
    private PhoneDao phoneDao;

    private final long EXISTING_PHONE_ID = 1000L;
    private final long NOT_EXISTING_PHONE_ID = 1500L;
    private final int AMOUNT_TO_FIND = 6;

    @Test
    public void testGetNotExistingPhone() {
        Optional<Phone> optionalPhone = phoneDao.get(NOT_EXISTING_PHONE_ID);
        Assert.assertFalse(optionalPhone.isPresent());
    }

    @Test
    public void testGetExistingPhone() {
        Optional<Phone> optionalPhone = phoneDao.get(EXISTING_PHONE_ID);
        Assert.assertTrue(optionalPhone.isPresent());
        Assert.assertTrue(EXISTING_PHONE_ID == optionalPhone.get().getId());
    }

    @Test
    public void testFindAll() {
        List<Phone> phoneList = phoneDao.findAll(0, AMOUNT_TO_FIND);
        Assert.assertTrue(phoneList.size() == AMOUNT_TO_FIND);
    }


    @Test
    public void testSaveExistingPhone() {
        Phone phone = phoneDao.get(EXISTING_PHONE_ID).get();
        String oldModel = phone.getModel();
        String newModel = oldModel + "newModel";
        phone.setModel(newModel);
        phoneDao.save(phone);
        phone = phoneDao.get(phone.getId()).get();
        Assert.assertTrue(phone.getModel().equals(newModel));
    }
}
