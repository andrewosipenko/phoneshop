package com.es.core.model.phone;

import com.es.core.AbstractTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration("classpath:context/testContext-core.xml")
public class JdbcProductDaoIntTest extends AbstractTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final Long NOT_EXIST_PHONE_ID = 10000L;

    private static final Long EXIST_PHONE_ID = 1000L;

    private Long phoneForUpdate;

    private static final String PHONE_TABLE_NAME = "phones";

    private static final String PHONE_COLORS_TABLE_NAME = "phone2color";


    @Before
    public void addPhoneForUpdate() {
        Phone phone = createPhone("initBrand", "initModel", null, 4);

        phoneDao.save(phone);

        phoneForUpdate = phone.getId();
    }

    @Test
    public void getNotExistPhone() {
        Optional<Phone> phoneOptional = phoneDao.get(NOT_EXIST_PHONE_ID);
        Assert.assertFalse(phoneOptional.isPresent());
    }

    @Test
    public void getExistPhone() {
        Optional<Phone> phoneOptional = phoneDao.get(EXIST_PHONE_ID);
        Assert.assertEquals(EXIST_PHONE_ID, phoneOptional.get().getId());
    }

    @Test
    public void checkFindAll() {
        final int OFFSET = 0;
        final int COUNT = 5;

        List<Phone> phoneList = phoneDao.findAll(OFFSET, COUNT);

        Assert.assertEquals(COUNT, phoneList.size());
    }

    @Test
    public void checkFindAllOrderBy() {
        final int OFFSET = 0;
        final int COUNT = 5;
        final PhoneDao.OrderBy ORDER_BY = PhoneDao.OrderBy.PRICE;

        List<Phone> phoneList = phoneDao.findAll(OFFSET, COUNT);
        phoneList.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
        List<Phone> sortedPhoneList = phoneDao.findAllInOrder(ORDER_BY, OFFSET, COUNT);

        Assert.assertEquals(sortedPhoneList.size(), phoneList.size());

        for (int i = 0; i < sortedPhoneList.size(); i++) {
            Assert.assertEquals(phoneList.get(i).getPrice(), sortedPhoneList.get(i).getPrice());
        }
    }

    @Test
    public void insertNewPhoneWithOneColors() {
        insertPhone(1);
    }

    @Test
    public void insertNewPhoneWithSeveralColors() {
        insertPhone(3);
    }

    @Test
    public void updatePhone() {
        final String newValue = "test";

        Phone phone = phoneDao.get(phoneForUpdate).get();
        phone.setBrand(newValue);
        phoneDao.save(phone);
        Phone updatedPhone = phoneDao.get(phoneForUpdate).get();
        Assert.assertEquals(newValue, updatedPhone.getBrand());
    }

    @Test
    public void updatePhoneChangeColorsCount() {
        Phone phone = phoneDao.get(phoneForUpdate).get();
        phone.setColors(new HashSet<>(colorList));
        phoneDao.save(phone);
        Phone updatedPhone = phoneDao.get(phoneForUpdate).get();
        Assert.assertEquals(colorList.size(), updatedPhone.getColors().size());
    }

    @Test
    public void checkPhonesCount() {
        List<Phone> phoneList = phoneDao.findAll(0, Integer.MAX_VALUE);

        long phonesCount = phoneDao.phonesCount();

        Assert.assertEquals(phonesCount, phoneList.size());
    }

    @Test
    public void findPhoneByBrand() {
        findInitPhone("initBrand");
    }

    @Test
    public void findPhoneByModel() {
        findInitPhone("initModel");
    }

    @Test
    public void findPhoneByPartOfName() {
        findInitPhone("nitBr");
    }

    @Test
    public void findNonexistentPhone() {
        final String QUERY = "hgfjhcfhjchfxcdgxyt";
        List<Phone> phoneList = phoneDao.getPhonesByQuery(QUERY, PhoneDao.OrderBy.BRAND, 0, 10);
        Assert.assertTrue(phoneList.isEmpty());
    }

    @Test
    public void findSeveralPhones() {
        final int COUNT = 2;
        final String QUERY = "test";

        phoneDao.save(createPhone("test1", "test1", null, 1));
        phoneDao.save(createPhone("test2", "test2", null, 2));

        List<Phone> phoneList = phoneDao.getPhonesByQuery(QUERY, PhoneDao.OrderBy.BRAND, 0, 10);
        Assert.assertEquals(COUNT, phoneList.size());
    }

    @Test
    public void checkPhonesCountByQuery() {
        final String QUERY = "test";

        phoneDao.save(createPhone("test1", "test1", null, 1));
        phoneDao.save(createPhone("test2", "test2", null, 2));

        List<Phone> phoneList = phoneDao.getPhonesByQuery(QUERY, PhoneDao.OrderBy.BRAND, 0, 10);
        int count = phoneDao.phonesCountByQuery(QUERY);
        Assert.assertEquals(count, phoneList.size());
    }

    @Test
    public void getPhonesByIdList() {
        final int COUNT = 5;
        List<Phone> phoneList = addNewPhones(COUNT);

        List<Long> idList = phoneList.stream().map(Phone::getId).collect(Collectors.toList());
        assertList(phoneList, phoneDao.getPhonesByIdList(idList));
    }

    @Test
    public void getNonexistentPhonesByIdList() {
        List<Long> idList = new ArrayList<>();
        idList.add(Long.MAX_VALUE - 100);
        idList.add(Long.MAX_VALUE - 2);
        idList.add(Long.MAX_VALUE);

        List<Phone> receivedPhones = phoneDao.getPhonesByIdList(idList);
        Assert.assertTrue(receivedPhones.isEmpty());
    }

    @Test
    public void getPhonesWithoutColors() {
        Phone phoneWithoutColors = createPhone("noColors","noColors",null,0);

        phoneDao.save(phoneWithoutColors);
        Optional<Phone> phoneOptional = phoneDao.get(phoneWithoutColors.getId());
        Assert.assertTrue(phoneOptional.isPresent());
        Assert.assertEquals(0,phoneOptional.get().getColors().size());
    }

    private void assertList(List<Phone> list1, List<Phone> list2) {
        Assert.assertEquals(list1.size(), list2.size());
        for (Phone phone : list1) {
            Assert.assertTrue(list2.contains(phone));
        }
    }

    private void findInitPhone(String query) {
        List<Phone> phoneList = phoneDao.getPhonesByQuery(query, PhoneDao.OrderBy.BRAND, 0, 10);
        Assert.assertEquals(phoneForUpdate, phoneList.get(0).getId());
    }

    private void insertPhone(int countColors) {
        Phone phone = createPhone(null, countColors);

        int startPhonesCount = countRowsInTable(PHONE_TABLE_NAME);

        int startPhoneColorsCount = countRowsInTable(PHONE_COLORS_TABLE_NAME);

        phoneDao.save(phone);

        Assert.assertEquals(startPhonesCount + 1, countRowsInTable(PHONE_TABLE_NAME));
        Assert.assertEquals(startPhoneColorsCount + countColors, countRowsInTable(PHONE_COLORS_TABLE_NAME));
    }

    private int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }
}
