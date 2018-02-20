package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDao implements PhoneDao{
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_GET_PHONE_QUERY = "SELECT * FROM phones WHERE id=?";
    private final static String SQL_GET_COLORS_QUERY = "SELECT code" +
                                                        "FROM colors LEFT JOIN phone2color ON colors.id = phone2color.colorID" +
                                                        "WHERE phoneID=?";

    public Optional<Phone> get(final Long key) {
        Phone phone = jdbcTemplate.queryForObject(
                SQL_GET_PHONE_QUERY,
                new Object[]{key},
                new BeanPropertyRowMapper<>(Phone.class)
        );

        if (phone != null){
            phone.setColors(queryColors(key));
        }

        return Optional.of(phone);
    }

    private Set<Color> queryColors(final Long key) {
        return new HashSet<>(jdbcTemplate.query(
                SQL_GET_COLORS_QUERY,
                new Object[]{key},
                (resultSet, i) -> {
                    Color color = new Color();
                    color.setCode(resultSet.getString("code"));
                    color.setId(resultSet.getLong("id"));
                    return color;
                }
        ));
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }
}
