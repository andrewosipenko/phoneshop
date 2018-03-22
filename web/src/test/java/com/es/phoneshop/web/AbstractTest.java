package com.es.phoneshop.web;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.BeforeClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbstractTest {

    protected static List<Color> colorList = new ArrayList<>();

    @BeforeClass
    public static void getListColors() {
        colorList.add(new Color(1000L, "Black"));
        colorList.add(new Color(1001L, "White"));
        colorList.add(new Color(1002L, "Yellow"));
        colorList.add(new Color(1003L, "Blue"));
        colorList.add(new Color(1004L, "Red"));
        colorList.add(new Color(1005L, "Purple"));
        colorList.add(new Color(1006L, "Gray"));
        colorList.add(new Color(1007L, "Green"));
        colorList.add(new Color(1008L, "Pink"));
        colorList.add(new Color(1009L, "Gold"));
        colorList.add(new Color(1010L, "Silver"));
        colorList.add(new Color(1011L, "Orange"));
        colorList.add(new Color(1012L, "Brown"));
        colorList.add(new Color(1013L, "256"));
    }


    protected Phone createPhone(Long id, int countColors) {
        return createPhone("testBrand", "testBrand", id, countColors);
    }

    protected Phone createPhone(String brand, String model, Long id, int countColors) {
        Phone phone = new Phone();
        phone.setBrand(brand);
        phone.setModel(model);
        phone.setPrice(new BigDecimal(600));

        Set<Color> colors = new HashSet<>(colorList.subList(0, countColors));

        phone.setColors(colors);
        phone.setId(id);
        return phone;
    }
}
