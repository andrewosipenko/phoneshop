package com.es.core.model.phone;

import com.es.core.exceptions.CustomNotFoundException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {

    private String SQL_statement = "select phones.id, phones.brand, phones.model, phones.price, phones.imageUrl, colors.code, colors.id " +
                                   "from phones join phone2color on phones.id = phone2color.phoneId " +
                                   "join colors on phone2color.colorId = colors.id ";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        List<Phone> resultList = jdbcTemplate.query(SQL_statement, new PhoneRowMapper());
        if (key.intValue() - 1000 >= resultList.size()) {
            throw new CustomNotFoundException("custom_404", "no such phone");
        } else {
            return Optional.of(resultList.get(key.intValue() - 1000));
        }
    }

    public void save(final Phone phoneToSave) {
        List<Phone> phoneList = jdbcTemplate.query("select * from phones", new BeanPropertyRowMapper<>(Phone.class));
        System.out.println(phoneList.size());
        int index = phoneList.indexOf(phoneToSave);
        if (index >= 0) {
            updateColorsDataBases(phoneToSave);
            jdbcTemplate.update("update phones set (phones.imageUrl, phones.price) = (?, ?)",
                    phoneToSave.getImageUrl(), phoneToSave.getPrice());
        } else if (index < 0) {
            jdbcTemplate.update("insert into phones (brand, model, price, imageUrl) values (?, ?, ?, ?)",
                    phoneToSave.getBrand(), phoneToSave.getModel(), phoneToSave.getPrice(), phoneToSave.getImageUrl());
            updateColorsDataBases(phoneToSave);
        }
    }

    private void updateColorsDataBases(Phone phoneToSave) {
        List<Color> colorList = jdbcTemplate.query("select * from colors", new BeanPropertyRowMapper<>(Color.class));
        for (Color currentColor : phoneToSave.getColors()) {
            if (!colorList.contains(currentColor)) {
                jdbcTemplate.update("insert into colors (code) values (?)", currentColor.getCode());
                String codeColor = currentColor.getCode();
                Color colorWithId = jdbcTemplate.query("select * from colors where colors.code = ?", new Object[]{codeColor},
                        new BeanPropertyRowMapper<>(Color.class)).get(0);
                jdbcTemplate.update("insert into phone2color (phoneId, colorId) values (?, ?)",
                        phoneToSave.getId().intValue(), colorWithId.getId().intValue());
            }
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        PhoneRowMapper phoneRowMapper = new PhoneRowMapper();
        jdbcTemplate.query(SQL_statement + "offset " + offset + " limit " + limit, phoneRowMapper);
        return phoneRowMapper.getFilledPhones();
    }

    public void delete(Phone phone) {
        Long idToDelete = phone.getId();
        List<Phone> list = jdbcTemplate.query("select * from phones", new BeanPropertyRowMapper<>(Phone.class));
        System.out.println(list.size());
        if (list.contains(phone)) {
            jdbcTemplate.update("delete from phones where phones.id = ? ", idToDelete);
            list = jdbcTemplate.query("select * from phones", new BeanPropertyRowMapper<>(Phone.class));
            System.out.println(list.size());
        } else
            return;
    }
}
