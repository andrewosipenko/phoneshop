package com.es.core.dao.phoneDao;

import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/applicationContext-test.xml")
public class JdbcPhoneDaoTest {

    @Autowired
    private PhoneDao phoneDao;

   /* @Test
    public void test() {
        assertThat(phoneDao).isNotNull();
    }*/

    @Test
    public void testFindAll() {
        List<Phone> all = phoneDao.findAll(10, 10);
        assertFalse(all.isEmpty());
    }
}
