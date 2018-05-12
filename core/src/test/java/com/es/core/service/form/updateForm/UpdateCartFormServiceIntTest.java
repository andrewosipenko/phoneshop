package com.es.core.service.form.updateForm;

import com.es.core.form.cart.updateForm.CartFormItem;
import com.es.core.form.cart.updateForm.UpdateCartForm;
import com.es.core.model.phone.Phone;
import com.es.core.service.form.update.UpdateCartFormService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class UpdateCartFormServiceIntTest {
    @Resource
    private UpdateCartFormService updateCartFormService;

    private final Long PHONE_ID_1 = 1000L;
    private final Long PHONE_ID_2 = 2000L;
    private final Long QUANTITY_1 = 2L;
    private final Long QUANTITY_2 = 4L;

    @Test
    public void getUpdateCartForm() {
        Phone phone1 = new Phone();
        phone1.setId(PHONE_ID_1);
        Phone phone2 = new Phone();
        phone2.setId(PHONE_ID_2);
        List<Phone> phones = Arrays.asList(phone1, phone2);
        Map<Long, Long> cartItems = new HashMap<>();
        cartItems.put(PHONE_ID_1, QUANTITY_1);
        cartItems.put(PHONE_ID_2, QUANTITY_2);

        UpdateCartForm updateCartForm = updateCartFormService.getUpdateCartForm(phones, cartItems);

        Assert.assertTrue(updateCartForm != null);
        Assert.assertTrue(updateCartForm.getCartFormItems().size() == 2);
    }

    @Test
    public void getCartItems() {
        UpdateCartForm updateCartForm = new UpdateCartForm();

        CartFormItem cartFormItem1 = new CartFormItem();
        cartFormItem1.setPhoneId(PHONE_ID_1);
        cartFormItem1.setQuantity(QUANTITY_1);

        CartFormItem cartFormItem2 = new CartFormItem();
        cartFormItem2.setPhoneId(PHONE_ID_2);
        cartFormItem2.setQuantity(QUANTITY_2);

        updateCartForm.setCartFormItems(Arrays.asList(cartFormItem1, cartFormItem2));

        Map<Long, Long> cartItems = updateCartFormService.getCartItems(updateCartForm);

        Assert.assertTrue(cartItems.size() == 2);
        Assert.assertTrue(cartItems.containsKey(PHONE_ID_1));
        Assert.assertTrue(cartItems.containsKey(PHONE_ID_2));
        Assert.assertTrue(cartItems.get(PHONE_ID_1).equals(QUANTITY_1));
        Assert.assertTrue(cartItems.get(PHONE_ID_2).equals(QUANTITY_2));
    }
}
