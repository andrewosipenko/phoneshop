package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:context/applicationContext-test-*.xml",
                                    "classpath:context/applicationContext-test.xml",
                                    "classpath:context/dispatcher-servlet-test.xml"})
public class ProductListPageControllerIntTest {

    @Resource
    private InternalResourceViewResolver internalResourceViewResolver;

    @Mock
    private PhoneDao phoneDao;

    @InjectMocks
    private ProductListPageController productListPageController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productListPageController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testShowProductList() throws Exception {
        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone());
        phones.add(new Phone());

        when(phoneDao.findAll(any(Integer.class), any(Integer.class))).thenReturn(phones);

        mockMvc.perform((get("/productList")))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attribute("phones", instanceOf(List.class)));
    }
}
