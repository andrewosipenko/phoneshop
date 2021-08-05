package com.es.core.model.phone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao{
    private static final String SELECT_ALL_PHONES_WITH_OFFSET_LIMIT = "select * from ( select * from phones offset ? limit ?) a left join phone2color on a.id = phone2color.phoneId left join colors on phone2color.colorId = colors.id";
    private static final String SELECT_PHONE_BY_ID = "select * from phones left join phone2color on phones.id = phone2color.phoneId left join colors on phone2color.colorId = colors.id where phones.id = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONE_BY_ID, new Object[] {key}, new PhoneExtractor());
        return phones.stream().findFirst();
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        if(offset < 0 ) throw new IllegalArgumentException();
        if(limit < 0) throw new IllegalArgumentException();
        return jdbcTemplate.query(SELECT_ALL_PHONES_WITH_OFFSET_LIMIT,
                new Object[] {offset, limit}, new PhoneExtractor());
    }
}
