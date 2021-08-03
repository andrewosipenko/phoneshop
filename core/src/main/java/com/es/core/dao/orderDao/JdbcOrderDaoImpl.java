package com.es.core.dao.orderDao;


import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcOrderDaoImpl implements OrderDao {

    private final static String FIND_ALL_QUARY = "select * from (select * from ORDERS offset ? limit ?) " +
            "t left join ORDER_ITEMS on t.ID = ORDER_ITEMS.ORDER_ID left join PHONES " +
            "on PHONES.ID = ORDER_ITEMS.PHONE_ID";
    private final JdbcTemplate jdbcTemplate;
    private final PhoneDao phoneDao;

    @Autowired
    public JdbcOrderDaoImpl(JdbcTemplate jdbcTemplate, PhoneDao phoneDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.phoneDao = phoneDao;
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        throw new UnsupportedOperationException();
        //   return jdbcTemplate.query(FIND_ALL_QUARY, new OrderFindAllMapper(phoneDao), offset, limit);
    }

    @Override
    public void save(Order model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Order model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Order model) {
        throw new UnsupportedOperationException();
    }
}
