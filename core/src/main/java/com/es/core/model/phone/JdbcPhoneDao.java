package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);
    private BeanPropertyRowMapper<Color> colorBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Color.class);

    public Optional<Phone> get(Long key) {
        Phone phone = this.jdbcTemplate.queryForObject("select * from phones where id = ?", new Object[]{key}, phoneBeanPropertyRowMapper);
        return Optional.ofNullable(phone);
    }

    public void save(Phone phone) {
        checkPhoneId(phone);
        Object[] valuesFromPhone = getValuesFromPhone(phone);
        if (valuesFromPhone != null) {
            this.jdbcTemplate.update("insert into phones values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", valuesFromPhone);
            for (Color color : phone.getColors()) {
                this.jdbcTemplate.update("insert into phone2color values (?, ?)", phone.getId(), color.getId());
            }
        }
    }

    private void checkPhoneId(Phone phone) {
        if (phone.getId() == null) {
            long id = this.jdbcTemplate.queryForObject("select max(id) from phones", int.class);
            phone.setId(id + 1);
        }
    }

    private Object[] getValuesFromPhone(Phone phone) {
        Object[] valuesFromPhone;
        try {
            Class<Phone> phoneClass = Phone.class;
            Field[] fieldsFromPhone = phoneClass.getDeclaredFields();
            ArrayList<Field> arrayOfFields = new ArrayList<>(Arrays.asList(fieldsFromPhone));
            arrayOfFields.remove("colors");
            valuesFromPhone = new Object[arrayOfFields.size()];
            for (int i = 0; i < valuesFromPhone.length; i++) {
                Field field = arrayOfFields.get(i);
                field.setAccessible(true);
                valuesFromPhone[i] = field.get(phone);
            }
            return valuesFromPhone;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = this.jdbcTemplate.query("select * from phones offset ? limit ?", new Object[]{offset, limit}, phoneBeanPropertyRowMapper);
        List<Color> colors = this.jdbcTemplate.query("select * from colors", colorBeanPropertyRowMapper);
        putColorsInPhones(phones, colors);
        return phones;
    }

    private void putColorsInPhones(List<Phone> phones, List<Color> colors) {
        Map<Long, String> colorsMap = colors.stream().collect(Collectors.toMap(Color::getId, Color::getCode));
        for (Phone phone : phones) {
            List<Long> colorId = this.jdbcTemplate.queryForList("select colorId from phone2color where phoneId = ?", new Object[]{phone.getId()}, long.class);
            Set<Color> colorsForPhone = new HashSet();
            for (Long id : colorId) {
                colorsForPhone.add(new Color(id, colorsMap.get(id)));
            }
            phone.setColors(colorsForPhone);
        }
    }

    public void delete(Phone phone) {
        this.jdbcTemplate.update("delete from phones where id = ?", phone.getId());
    }
}
