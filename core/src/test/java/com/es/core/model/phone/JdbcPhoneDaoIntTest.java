package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationTestContext-core.xml")
public class JdbcPhoneDaoIntTest {
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
    public void phoneInsertionKeyCheck() {
        Phone phone = phoneDao.get(1001L).get();
        phone.setId(null);
        phone.setBrand("Brandfff");
        phone.setModel("Modelfff");
        phoneDao.save(phone);
        assertTrue(phoneDao.get(phone.getId()).isPresent());
    }
}
