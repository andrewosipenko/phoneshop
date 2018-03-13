package com.es.core;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

public class AbstractTest {

    @Resource
    protected PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

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

    protected List<Phone> addNewPhones(int count) {
        List<Phone> phoneList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Phone newPhone = createPhone("test" + Integer.toString(i), "test" + Integer.toString(i), null, 2);
            phoneDao.save(newPhone);
            phoneList.add(newPhone);
        }

        return phoneList;
    }

    protected void setStocks(Map<Long, Long> phoneStockMap) {
        for (Map.Entry<Long, Long> entry : phoneStockMap.entrySet()) {
            int count = jdbcTemplate.queryForObject("select count(1) from stocks where phoneId = ?",
                    Integer.class, entry.getKey());
            if (count == 0) {
                jdbcTemplate.update("insert into stocks (phoneId, stock, reserved) values (?, ?, 0)",
                        entry.getKey(), entry.getValue());
            } else {
                jdbcTemplate.update("update stocks set stock = ?, reserved = 0 where phoneId = ?",
                        entry.getValue(), entry.getKey());
            }
        }
    }

}
