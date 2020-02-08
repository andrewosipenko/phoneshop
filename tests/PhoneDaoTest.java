import com.es.core.model.phone.JdbcPhoneDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:D:\\3k\\ES\\phoneshop\\core\\src\\main\\resources\\context\\applicationContext-core.xml")
@WebAppConfiguration
public class PhoneDaoTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private JdbcPhoneDao jdbcPhoneDao;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        jdbcPhoneDao = (JdbcPhoneDao)wac.getBean("jdbcPhoneDao");
    }

    @Test
    public void get(final Long key) {

    }

    @Test
    public void notEmptyPhonesDataBaseWhenCustomProductDaoTestGetProductList() {
        assertFalse(jdbcPhoneDao.findAll(0, 5).isEmpty());
    }

}
