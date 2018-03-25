package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationTestContext-core.xml")
public class JdbcPhoneDaoIntTest {

    private final Long phoneIdWithEmptyColorSet = 1013L;
    private final Set<Color> expectedColorSetForPhoneId1017 = new HashSet<>(Arrays.asList(
            new Color(1000L,"Black"),
            new Color(1003L, "Blue"),
            new Color(1004L, "Red")
    ));
    @Resource
    private PhoneDao phoneDao;

    @Test
    @Transactional
    public void testGetLowerBoundInPhones() {
        assertEquals((long)phoneDao.get(1000L).get().getId(),1000L);
    }

    @Test
    @Transactional
    public void testGetUpperBoundInPhones() {
        assertEquals((long)phoneDao.get(1999L).get().getId(),1999L);
    }

    @Test
    @Transactional
    public void testPhoneUpdate() {
        Phone phone = phoneDao.get(1001L).get();
        phone.setDescription("");
        phoneDao.save(phone);
        assertEquals(phoneDao.get(1001L).get().getDescription(),"");
    }

    @Test
    @Transactional
    public void testPhoneInsertSizeIncrement() {
        int sizeBeforeInsertion = phoneDao.findAll(0, 0).size();
        Phone phone = new Phone();
        phone.setBrand("Brandfff");
        phone.setModel("Modelfff");
        phoneDao.save(phone);
        assertEquals(phoneDao.findAll(0, 0).size() - 1, sizeBeforeInsertion);
    }

    @Test
    @Transactional
    public void testPhoneInsert() {
        Phone phone = new Phone();
        phone.setBrand("Brandfff");
        phone.setModel("Model");
        phoneDao.save(phone);
        Phone insertedPhone = phoneDao.get(phone.getId()).get();
        assertTrue(insertedPhone.getId().equals(phone.getId()));
        assertTrue(insertedPhone.getBrand().equals(phone.getBrand()));
        assertTrue(insertedPhone.getModel().equals(phone.getModel()));
    }

    @Test
    @Transactional
    public void testGetColorSet() {
        Set<Color> actualColorset = phoneDao.get(1017L).get().getColors();
        actualColorset.equals(expectedColorSetForPhoneId1017);
    }

    @Test
    @Transactional
    public void testGetColorSetAfterUpdate() {
        Phone phone = phoneDao.get(1017L).get();
        Set<Color> expectedColorSet = phone.getColors();
        expectedColorSet.add(new Color(1010L, "Silver"));
        phone.setColors(expectedColorSet);
        phoneDao.save(phone);
        Set<Color> actualColorSet = phoneDao.get(1017L).get().getColors();
        assertTrue(actualColorSet.equals(expectedColorSet));
    }

    @Test
    @Transactional
    public void testGetEmptyColorSet() {
        Set<Color> actualColorSet = phoneDao.get(phoneIdWithEmptyColorSet).get().getColors();
        Set<Color> expectedColorSet = new HashSet<>();
        assertTrue(actualColorSet.equals(expectedColorSet));
    }
}
