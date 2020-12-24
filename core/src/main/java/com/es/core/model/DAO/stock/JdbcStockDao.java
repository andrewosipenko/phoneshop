package com.es.core.model.DAO.stock;

import com.es.core.model.DAO.exceptions.IdUniquenessException;
import com.es.core.model.DAO.phone.PhoneDao;
import com.es.core.model.entity.phone.Phone;
import com.es.core.model.entity.phone.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStockDao implements StockDao {
    public static final String SELECT_ONE_BY_PHONE_ID_SQL_QUERY = "SELECT * FROM stocks WHERE phoneId = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PhoneDao phoneDao;

    @Override
    public Optional<Stock> get(Long phoneId) {
        List<Stock> queryResult = jdbcTemplate.query(SELECT_ONE_BY_PHONE_ID_SQL_QUERY, new BeanPropertyRowMapper<>(Stock.class), phoneId);
        Optional<Phone> phone = phoneDao.get(phoneId);
        if (queryResult.size() > 1) {
            throw new IdUniquenessException(phoneId, queryResult.size());
        }
        if (queryResult.isEmpty() || !phone.isPresent()) {
            return Optional.empty();
        }
        queryResult.get(0).setPhone(phone.get());
        return Optional.of(queryResult.get(0));
    }

    @Override
    public List<Stock> getCorrespondingValue(List<Long> phoneIds) {
        return null;
    }
}
