package com.es.core.dao.productDao;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.dao.SqlQueryConstants;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
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
    @Resource
    private JdbcTemplate jdbcTemplate;
    private final long FIRST_PHONE_ID = 1000L;
    private final long EXISTING_PHONE_ID = 1000L;
    private final long NOT_EXISTING_PHONE_ID = 1500L;
    private final int AMOUNT_TO_FIND = 6;
    private final int AMOUNT_OF_AVAILABLE_PHONES = 6;
    private final String SEARCH_ALL = "%";
    private final String SEARCH_CONCRETE_EXISTING = "ARCHOS";
    private final String SEARCH_CONCRETE_NOT_EXISTING = "NotExisting";

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
        List<Phone> phoneList = phoneDao.findAll(0, AMOUNT_TO_FIND, SEARCH_ALL);
        Assert.assertTrue(phoneList.size() == AMOUNT_TO_FIND);
    }

    @Test
    public void testFindAllSortedBy() {
        String sortDirection = "asc";
        String fieldToBeSortedBy = "price";
        List<Phone> phones = phoneDao.findAllSortedBy(0, AMOUNT_OF_AVAILABLE_PHONES, SEARCH_CONCRETE_EXISTING,
                fieldToBeSortedBy, sortDirection);
        for (int i = 0; i < phones.size(); ++i) {
            Assert.assertTrue(phones.get(i).getBrand().contains(SEARCH_CONCRETE_EXISTING));
            if (i < phones.size() - 1) {
                Phone currentPhone = phones.get(i);
                Phone nextPhone = phones.get(i + 1);
                if ("asc".equals(sortDirection)) {
                    Assert.assertTrue(currentPhone.getPrice().compareTo(nextPhone.getPrice()) <= 0);
                } else {
                    Assert.assertTrue(currentPhone.getPrice().compareTo(nextPhone.getPrice()) >= 0);
                }
            }
        }
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

    @Test
    public void testSaveNotExistingPhone() {
        Phone phone = new Phone();
        String model = "checkModel";
        String brand = "checkBrand";
        phone.setModel(model);
        phone.setBrand(brand);
        int phonesCount = jdbcTemplate.queryForObject(SqlQueryConstants.PhoneDao.COUNT_PHONES, Integer.class);
        phoneDao.save(phone);
        Assert.assertEquals(model, phoneDao.get(phone.getId()).get().getModel());
        Assert.assertEquals(brand, phoneDao.get(phone.getId()).get().getBrand());
        Assert.assertTrue(phone.getId() == FIRST_PHONE_ID + phonesCount);
    }

    @Test
    public void testCountAvailablePhones() {
        int amount = phoneDao.countAvailablePhone(SEARCH_ALL);
        Assert.assertEquals(AMOUNT_OF_AVAILABLE_PHONES, amount);
    }

    @Test
    public void testContainsAvailablePhone() {
        Assert.assertTrue(phoneDao.contains(EXISTING_PHONE_ID));
    }

    @Test
    public void testContainsUnavailablePhone() {
        Assert.assertFalse(phoneDao.contains(NOT_EXISTING_PHONE_ID));
    }
}