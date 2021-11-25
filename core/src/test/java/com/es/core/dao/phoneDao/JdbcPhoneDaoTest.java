package com.es.core.dao.phoneDao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/applicationContext-test.xml")
public class JdbcPhoneDaoTest {

    private static final long CORRECT_PHONE_ID = 101l;
    private static final long INCORRECT_PHONE_ID = 100001l;
    @Resource
    private PhoneDao phoneDao;
    private Phone testPhone01;
    private Phone testPhone02;
    private Set<Color> testColorSet = new HashSet<>();

    @Before
    public void setup() {
        testPhone01 = new Phone();
        testPhone02 = new Phone();

        testPhone01.setBrand("Aplle");
        testPhone01.setModel("Iphone13");
        testPhone01.setPrice(BigDecimal.valueOf(999));

        testPhone02.setBrand("Samsung");
        testPhone02.setModel("Galaxy Note 21");
        testPhone02.setPrice(BigDecimal.valueOf(999));

        Color black = new Color(1L, "Black");
        Color white = new Color(2L, "White");
        Color yellow = new Color(3L, "Yellow");
        Color grey = new Color(4L, "Grey");
        Color green = new Color(5L, "Green");

        testColorSet.add(grey);
        testColorSet.add(white);
    }

    @Test
    public void saveTest() {
        phoneDao.save(testPhone02);

        Long id = testPhone02.getId();

        final Optional<Phone> phoneFromDao = phoneDao.get(id);
        assertTrue(phoneFromDao.isPresent());
        assertEquals(phoneFromDao.get().getBrand(), testPhone02.getBrand());
        assertEquals(phoneFromDao.get().getModel(), testPhone02.getModel());
        testPhone01.setId(testPhone02.getId());
        phoneDao.save(testPhone01);
        final Optional<Phone> phoneFromDao2 = phoneDao.get(id);
        assertTrue(phoneFromDao2.isPresent());
        assertEquals(phoneFromDao2.get().getBrand(), testPhone01.getBrand());
        assertEquals(phoneFromDao2.get().getModel(), testPhone01.getModel());

    }

    @Test(expected = IllegalArgumentException.class)
    public void getTestIllegalArgException() {
        phoneDao.get(null);
    }

    @Test
    public void getTestException() {
        Optional<Phone> phone = phoneDao.get(INCORRECT_PHONE_ID);
        assertFalse(phone.isPresent());
    }

    @Test
    public void testFindAll() {
        List<Phone> phones = phoneDao.findAll(0, 10);
        assertEquals(2, phones.size());
    }

}
