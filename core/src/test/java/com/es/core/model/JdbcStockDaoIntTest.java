package com.es.core.model;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:context/applicationContext-test-*.xml"})
public class JdbcStockDaoIntTest {

    @Resource
    private JdbcStockDao jdbcStockDao;

    @Resource
    private ProductDao productDao;

    @Test
    public void testGetAllAvailableStocks() {
        List<Stock> actual = jdbcStockDao.getAllAvailableStocks();
        List<Stock> expected = getAllExpectedAvailableStocks();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetPhonesOf_1_pageWithLimit_2() {
        /**
         * the same in this case
         */
        List<Stock> expected = getAllExpectedAvailableStocks();
        List<Stock> actual = jdbcStockDao.getAvailableStocksByPage(1, 2);
        Assert.assertEquals(expected, actual);
    }

    private List<Stock> getAllExpectedAvailableStocks() {
        Stock stock = new Stock();
        Phone phone = productDao.loadPhoneById(1999);
        stock.setPhone(phone);
        stock.setStock(19);
        stock.setReserved(8);
        return Stream.of(stock).collect(Collectors.toList());
    }

}
