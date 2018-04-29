package com.es.core;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.dao.phoneDao.SqlQueryConstants;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcProductDaoIntTest{
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private JdbcTemplate jdbcTemplate;
    private final long FIRST_PHONE_ID = 1000L;
    private final long EXISTING_PHONE_ID = 1000L;
    private final long NOT_EXISTING_PHONE_ID = 1500L;
    private final int AMOUNT_TO_FIND = 6;
    private final int AMOUNT_OF_AVAILABLE_PHONES = 6;
    private final String SEARCH = "%";
    private final long PHONE_WITH_STOCK_ID_1 = 1001L;
    private final long PHONE_WITH_STOCK_ID_2 = 1004L;
    private final long PHONE_STOCK_1 = 0L;
    private final long PHONE_RESERVED_1 = 0L;
    private final long PHONE_STOCK_2 = 14L;
    private final long PHONE_RESERVED_2 = 3L;

    @Test
    public void testGetNotExistingPhone(){
        Optional<Phone> optionalPhone = phoneDao.get(NOT_EXISTING_PHONE_ID);
        Assert.assertFalse(optionalPhone.isPresent());
    }

    @Test
    public void testGetExistingPhone(){
        Optional<Phone> optionalPhone = phoneDao.get(EXISTING_PHONE_ID);
        Assert.assertTrue(optionalPhone.isPresent());
        Assert.assertTrue(EXISTING_PHONE_ID == optionalPhone.get().getId());
    }

    @Test
    public void testFindAll(){
        List<Phone> phoneList = phoneDao.findAll(0, AMOUNT_TO_FIND, SEARCH);
        Assert.assertTrue(phoneList.size() == AMOUNT_TO_FIND);
    }

    @Test
    public void testSaveExistingPhone(){
        Phone phone = phoneDao.get(EXISTING_PHONE_ID).get();
        String oldModel = phone.getModel();
        String newModel = oldModel + "newModel";
        phone.setModel(newModel);
        phoneDao.save(phone);
        phone = phoneDao.get(phone.getId()).get();
        Assert.assertTrue(phone.getModel().equals(newModel));
    }

    @Test
    public void testSaveNotExistingPhone(){
        Phone phone = new Phone();
        String model = "checkModel";
        String brand = "checkBrand";
        phone.setModel(model);
        phone.setBrand(brand);
        int phonesCount = jdbcTemplate.queryForObject(SqlQueryConstants.COUNT_PHONES, Integer.class);
        phoneDao.save(phone);
        Assert.assertEquals(model, phoneDao.get(phone.getId()).get().getModel());
        Assert.assertEquals(brand, phoneDao.get(phone.getId()).get().getBrand());
        Assert.assertTrue(phone.getId() == FIRST_PHONE_ID + phonesCount);
    }

    @Test
    public void testCountAvailablePhones(){
        int amount = phoneDao.countAvailablePhone(SEARCH);
        Assert.assertEquals(AMOUNT_OF_AVAILABLE_PHONES, amount);
    }

    @Test
    public void testContainsAvailablePhone(){
        Assert.assertTrue(phoneDao.contains(EXISTING_PHONE_ID));
    }

    @Test
    public void testContainsUnavailablePhone(){
        Assert.assertFalse(phoneDao.contains(NOT_EXISTING_PHONE_ID));
    }

    @Test
    public void testGetPhonesStocks(){
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        phone1.setId(PHONE_WITH_STOCK_ID_1);
        phone2.setId(PHONE_WITH_STOCK_ID_2);
        List<Phone> phones = Arrays.asList(phone1, phone2);
        List<Stock> stocks = phoneDao.getPhonesStocks(phones);
        Assert.assertTrue(stocks.size() == phones.size());
        Stock stock1 = stocks.get(0);
        Stock stock2 = stocks.get(1);

        Assert.assertTrue(stock1.getPhone().equals(phone1));
        Assert.assertTrue(stock2.getPhone().equals(phone2));

        Assert.assertTrue(stock1.getReserved().equals(PHONE_RESERVED_1));
        Assert.assertTrue(stock2.getReserved().equals(PHONE_RESERVED_2));

        Assert.assertTrue(stock1.getStock().equals(PHONE_STOCK_1));
        Assert.assertTrue(stock2.getStock().equals(PHONE_STOCK_2));
    }
}