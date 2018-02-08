package com.es.core.model.phone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration("classpath:context/testContext.xml")
public class JdbcProductDaoIntTest {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final Long NOT_EXIST_PHONE_ID = 10000L;

    private static final Long EXIST_PHONE_ID = 1000L;

    private Long phoneForUpdate;

    private static final String PHONE_TABLE_NAME = "phones";

    private static final String PHONE_COLORS_TABLE_NAME = "phone2color";

    private static List<Color> colorList = new ArrayList<>();

    @BeforeClass
    public static void getListColors() {
        colorList.add(new Color(1000L, "Black"));
        colorList.add(new Color(1001L, "White"));
        colorList.add(new Color(1002L, "Yellow"));
        colorList.add(new Color(1003L, "Blue"));
        colorList.add(new Color(1004L, "Red"));
        colorList.add(new Color(1005L, "Purple"));
        colorList.add(new Color(1006L, "Gray"));
        colorList.add(new Color(1007L, "Green"));
        colorList.add(new Color(1008L, "Pink"));
        colorList.add(new Color(1009L, "Gold"));
        colorList.add(new Color(1010L, "Silver"));
        colorList.add(new Color(1011L, "Orange"));
        colorList.add(new Color(1012L, "Brown"));
        colorList.add(new Color(1013L, "256"));
    }

    @Before
    public void addPhoneForUpdate() {
        Phone phone = createPhone("init", "init", null, 4);

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
        final String ORDER_BY = "price";

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

    private void insertPhone(int countColors) {
        Phone phone = createPhone(null, countColors);

        int startPhonesCount = countRowsInTable(PHONE_TABLE_NAME);

        int startPhoneColorsCount = countRowsInTable(PHONE_COLORS_TABLE_NAME);

        phoneDao.save(phone);

        Assert.assertEquals(startPhonesCount + 1, countRowsInTable(PHONE_TABLE_NAME));
        Assert.assertEquals(startPhoneColorsCount + countColors, countRowsInTable(PHONE_COLORS_TABLE_NAME));
    }

    private Phone createPhone(Long id, int countColors) {
        return createPhone("testBrand", "testBrand", id, countColors);
    }

    private Phone createPhone(String brand, String model, Long id, int countColors) {
        Phone phone = new Phone();
        phone.setBrand(brand);
        phone.setModel(model);

        Set<Color> colors = new HashSet<>(colorList.subList(0, countColors));

        phone.setColors(colors);
        phone.setId(id);
        return phone;
    }

    private int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }
}
