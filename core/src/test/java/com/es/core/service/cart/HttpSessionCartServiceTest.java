package com.es.core.service.cart;

import com.es.core.exceptions.phone.PhoneException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context/test-config.xml")
public class HttpSessionCartServiceTest {

    @Test
    public void shouldAddPhone(){

    }

    @Test(expected = PhoneException.class)
    public void shouldThrowPhoneExceptionAddPhone(){

    }

    @Test
    public void shouldUpdateItems(){

    }

    @Test(expected = PhoneException.class)
    public void shouldThrowPhoneExceptionUpdateItems(){

    }

    @Test
    public void shouldRemovePhone(){

    }

    @Test(expected = PhoneException.class)
    public void shouldThrowPhoneExceptionRemovePhone(){

    }
}
