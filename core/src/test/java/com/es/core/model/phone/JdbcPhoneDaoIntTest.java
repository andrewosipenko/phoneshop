package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationTestContext-core.xml")
public class JdbcPhoneDaoIntTest {

    private final Set<Color> expectedColorSetForPhoneId1017 = new HashSet<>(Arrays.asList(
            new Color(1000L,"Black"),
            new Color(1003L, "Blue"),
            new Color(1004L, "Red")
    ));
    @Resource
    private PhoneDao phoneDao;

    @Test
    public void lowerBoundInPhones() {
        assertEquals((long)phoneDao.get(1000L).get().getId(),1000L);
    }

    @Test
    public void upperBoundInPhones() {
        assertEquals((long)phoneDao.get(1999L).get().getId(),1999L);
    }

    @Test
    public void phoneUpdate() {
        Phone phone = phoneDao.get(1001L).get();
        phone.setDescription("");
        phoneDao.save(phone);
        assertEquals(phoneDao.get(1001L).get().getDescription(),"");
    }

    @Test
    public void phoneInsertionSizeIncrementionCheck() {
        int sizeBeforeInsertion = phoneDao.findAll(0, 0).size();
        Phone phone = new Phone();
        phone.setBrand("Brandfff");
        phone.setModel("Modelfff");
        phoneDao.save(phone);
        assertEquals(phoneDao.findAll(0, 0).size() - 1, sizeBeforeInsertion);
    }

    @Test
    public void phoneInsertionCheck() {
        Phone phone = new Phone();
        phone.setBrand("Brandfff");
        phone.setModel("Model");
        phoneDao.save(phone);
        assertTrue(phoneDao.get(phone.getId()).get().equals(phone));
    }

    @Test
    public void colorSetCheck() {
        Set<Color> actualColorset = phoneDao.get(1017L).get().getColors();
        actualColorset.equals(expectedColorSetForPhoneId1017);
    }

    @Test
    public void colorSetAfterUpdateCheck() {
        Phone phone = phoneDao.get(1017L).get();
        Set<Color> expectedColorSet = phone.getColors();
        expectedColorSet.add(new Color(1010L, "Silver"));
        phone.setColors(expectedColorSet);
        phoneDao.save(phone);
        Set<Color> actualColorSet = phoneDao.get(1017L).get().getColors();
        assertTrue(actualColorSet.equals(expectedColorSet));
    }

    @Test
    public void emptyColorSetCheck() {
        Set<Color> actualColorSet = phoneDao.get(1013L).get().getColors();
        Set<Color> expectedColorSet = new HashSet<>();
        assertTrue(actualColorSet.equals(expectedColorSet));
    }
}
