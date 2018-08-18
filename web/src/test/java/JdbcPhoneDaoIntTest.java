import com.es.core.model.phone.JdbcPhoneDao;
import org.junit.Assert;
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

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
@WebAppConfiguration
public class JdbcPhoneDaoIntTest{
@Autowired
private WebApplicationContext wac;
private MockMvc mockMvc;

@Before
    public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
}

    @Test
    public void checkOkStatus_productList() throws Exception{
    this.mockMvc.perform(get("/productList")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void checkOkStatus_productDetails() throws Exception{
    this.mockMvc.perform(get("/productDetails/1080")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void checkWrongFormatStatus_productDetails() throws Exception{
        this.mockMvc.perform(get("/productDetails/String")).andDo(print()).andExpect(status().is(400));
    }

    @Test
    public void checkNotNull_Get(){
    Assert.assertNotNull(wac.getBean(JdbcPhoneDao.class).get(1100L));
    }

    @Test
    public void checkInstanceOf_Get(){
    Assert.assertTrue(wac.getBean(JdbcPhoneDao.class).get(1100L) instanceof Optional);
    }

    @Test
    public void checkEmptyOptional_Get() {
    Assert.assertEquals(Optional.empty(), wac.getBean(JdbcPhoneDao.class).get(0L));
    }

    @Test
    public void checkNonEmptyOptional_Get(){
    Assert.assertNotEquals(Optional.empty(), wac.getBean(JdbcPhoneDao.class).get(1100L));
    }

    @Test
    public void checkNotNull_FindAll(){
    Assert.assertNotNull(wac.getBean(JdbcPhoneDao.class).findAll(10, 15));
    }

    @Test
    public void checkListLength_FindAll(){
    Assert.assertEquals(15, wac.getBean(JdbcPhoneDao.class).findAll(10, 15).size());
    }

    @Test
    public void checkNotNull_GetPhoneColors(){
    Assert.assertNotNull(wac.getBean(JdbcPhoneDao.class).getPhoneColors(1080L));
    }
}
