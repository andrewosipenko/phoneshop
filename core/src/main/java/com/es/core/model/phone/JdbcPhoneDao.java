package com.es.core.model.phone;

import com.es.core.model.mapper.PhoneRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao extends AbstractJdbcPhoneDao implements PhoneDao {

    public Optional<Phone> get(final Long key) {

      List<Phone> phoneOfDifferentColors = jdbcTemplate.query(PhoneQueries.GET_BY_KEY_QUERY, new Object[] {key}, new PhoneRowMapper());
      if(phoneOfDifferentColors.size() == 0) {
          return Optional.empty();
      }
      Phone phone = phoneOfDifferentColors.get(0);

      phone.setColors(getPhoneColors(phoneOfDifferentColors));
      phone.setId(key);
      return Optional.of(phone);
    }

    public void save(Phone phone) {
        if(phone.getId() == null) {
            insert(phone);
        } else {
            update(phone);
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query( getFindAllQueryString(offset, limit), new BeanPropertyRowMapper<>(Phone.class));
    }
}

