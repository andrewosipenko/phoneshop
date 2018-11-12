package com.es.core.model.phone;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@Lazy
@Repository
public class JdbcProductDao implements PhoneDao {
    private static final String SQL_QUERY_FOR_GETTING_ALL_COLORS = "select * from colors";
    private static final String SQL_FOR_GETTING_PHONE_BY_ID = "select * from phones where phones.id=?";
    private static final String SQL_FOR_GETTING_LAST_PHONE_ID = "select MAX(id) as lastId from phones";
    private static final String SQL_FOR_ADDING_PHONE = "insert into phones values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_FOR_BINDING_PHONE_AND_COLOR = "insert into phone2color values (?,?)";
    private static final String SQL_FOR_GETTING_PHONES_BY_OFFSET_AND_LIMIT = "select * from phones offset ? limit ?";
    private static final String SQL_FOR_DELETING_PHONE_BY_ID = "delete from phones where id = ?";
    private static final String SQL_FOR_GETTING_COLORS_BY_PHONE_ID = "select * from phone2color where phone2color.phoneId = ?";
    @Resource
    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);

    public void save(Phone phone) {
        checkPhoneIdAndSetIfNeeded(phone);
        Object[] phoneParameters = getPhoneFieldsValues(phone);
        jdbcTemplate.update(SQL_FOR_ADDING_PHONE, phoneParameters);
        bindPhoneAndColor(phone);
    }

    private void checkPhoneIdAndSetIfNeeded(Phone phone) {
        if (phone.getId() == null) {
            jdbcTemplate.query(SQL_FOR_GETTING_LAST_PHONE_ID,
                    (ResultSet resultSet) -> phone.setId(resultSet.getLong("lastId") + 1));
        } else {
            delete(phone.getId());
        }
    }

    private void delete(Long key) {
        jdbcTemplate.update(SQL_FOR_DELETING_PHONE_BY_ID, key);
    }

    private void bindPhoneAndColor(Phone phone) {
        if (!phone.getColors().equals(Collections.EMPTY_SET)) {
            for (Color color : phone.getColors()) {
                jdbcTemplate.update(SQL_FOR_BINDING_PHONE_AND_COLOR, phone.getId(), color.getId());
            }
        }
    }

    public Optional<Phone> get(Long key) {
        Map<Long, Color> colors = getColors();
        Optional<Phone> phone = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FOR_GETTING_PHONE_BY_ID,
                (resultSet, i) -> phoneBeanPropertyRowMapper.mapRow(resultSet, 0), key));
        phone.ifPresent((p) -> setColorsForPhone(p, colors));
        return phone;
    }

    private Map<Long, Color> getColors() {
        return jdbcTemplate.query(SQL_QUERY_FOR_GETTING_ALL_COLORS,
                new BeanPropertyRowMapper<>(Color.class)).stream().collect(Collectors.toMap(Color::getId, (c) -> c));
    }

    public List<Phone> findAll(int offset, int limit) {
        Map<Long, Color> colors = getColors();
        List<Phone> phones = jdbcTemplate.query(SQL_FOR_GETTING_PHONES_BY_OFFSET_AND_LIMIT,
                        phoneBeanPropertyRowMapper, offset, limit);
        for (Phone phone : phones) {
            setColorsForPhone(phone, colors);
        }
        return phones;
    }

    private void setColorsForPhone(Phone phone, Map<Long, Color> colors) {
        phone.setColors(new HashSet<>());
        jdbcTemplate.query(SQL_FOR_GETTING_COLORS_BY_PHONE_ID,
                        (resultSet, i) -> phone.getColors().add(colors.get(resultSet.getLong("colorId"))), phone.getId());
    }

    private Object[] getPhoneFieldsValues(Phone phone) {
        try {
            Field[] fields = phone.getClass().getDeclaredFields();
            List<Field> fieldsList = new ArrayList<>(Arrays.asList(fields));
            for (Field field : fields) {
                if (field.getName().equals("colors")) {
                    fieldsList.remove(field);
                    break;
                }
            }
            Object[] values = new Object[fieldsList.size()];
            for (int i = 0; i < fieldsList.size(); i++) {
                Field field = phone.getClass().getDeclaredField(fieldsList.get(i).getName());
                field.setAccessible(true);
                values[i] = field.get(phone);
            }
            return values;
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
