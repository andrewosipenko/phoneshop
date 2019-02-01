package com.es.core.util;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;

import java.util.Set;

public class PhoneCreator {
    public static Phone createPhone(Long id, String brand, String model, Set<Color> colors) {
        Phone phone = new Phone();
        phone.setColors(colors);
        phone.setId(id);
        phone.setModel(model);
        phone.setBrand(brand);

        return phone;
    }
}
