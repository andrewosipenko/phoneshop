package util;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;

public final class PhoneUtils {
    public static Phone createPhone(long i) {
        Phone phone = new Phone();
        phone.setId(i);
        phone.setBrand("testBrand-" + i);
        phone.setModel("testModel-" + i);
        phone.setPrice(BigDecimal.valueOf(i * 100D));
        return phone;
    }
}
