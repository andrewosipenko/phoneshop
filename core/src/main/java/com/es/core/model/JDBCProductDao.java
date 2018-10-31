package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component("JDBCProductDao")
public class JDBCProductDao implements PhoneDao {
    @Resource
    private JdbcTemplate phone2colorJdbcTemplate;

    public Optional<Phone> get(final Long key) {
        throw new UnsupportedOperationException("TODO");
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        Map<Long, Color> colors = (Map<Long, Color>)phone2colorJdbcTemplate.query("select * from colors", new BeanPropertyRowMapper(Color.class)).stream()
                .collect(Collectors.toMap((c) -> ((Color)c).getId(), (c) -> c));
        List<Phone> phones = phone2colorJdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
        for (Phone phone : phones) {
            phone.setColors(new HashSet<>());
            phone2colorJdbcTemplate.query("select * from phone2color where phone2color.phoneId = "+phone.getId(), new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet resultSet) throws SQLException {
                    phone.getColors().add(colors.get(resultSet.getLong("colorId")));
                }
            });
        }
        return phones;
    }
}
