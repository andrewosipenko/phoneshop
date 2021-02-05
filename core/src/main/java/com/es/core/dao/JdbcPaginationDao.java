package com.es.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class JdbcPaginationDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String COUNT_PAGES_IN_SEARCHING_BY_QUERY = "select count(distinct resultTable.id) from (select phoneWithPriceStock.id " +
            "from (select * from phones join stocks on stocks.phoneId=id )" +
            "as phoneWithPriceStock join phone2color as p2c on phoneWithPriceStock.id=p2c.phoneId " +
            "join colors on p2c.colorId=colors.id where ((lower(phoneWithPriceStock.model) like ? or lower(phoneWithPriceStock.description) like ?) " +
            "and phoneWithPriceStock.price is not null and phoneWithPriceStock.stock > 0 )) as resultTable";

    public double countPages(String query) {
        double counter = 0;
        List<String> queryItems = Arrays.asList(query.split(" "));
        if (queryItems.size() == 0) {
            counter = jdbcTemplate.queryForObject(COUNT_PAGES_IN_SEARCHING_BY_QUERY, Double.class, new Object[]{"%%", "%%"});
        } else {
            for (String qr : queryItems) {
                counter += jdbcTemplate.queryForObject(COUNT_PAGES_IN_SEARCHING_BY_QUERY, Double.class, new Object[]{
                        "%" + qr.toLowerCase() + "%", "%" + qr.toLowerCase() + "%"});
            }
        }
        return counter;
    }

}
