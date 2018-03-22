package com.es.core.dao.stock;

import com.es.core.AbstractTest;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration("classpath:context/testContext-core.xml")
public class JdbcStockDaoIntTest extends AbstractTest {

    @Resource
    private StockDao stockDao;

    @Test
    public void getStocksCheck() {
        final int COUNT = 5;
        List<Phone> phoneList = addNewPhones(COUNT);

        List<Long> phoneIdList = phoneList.stream()
                .map(Phone::getId).collect(Collectors.toList());

        Map<Long, Long> stockMap = phoneIdList.stream()
                .collect(Collectors.toMap(o -> o, o -> (long) (o.hashCode() % 15)));

        setStocks(stockMap);

        List<Stock> stockList = stockDao.getStocks(phoneIdList);
        for (Stock stock : stockList) {
            Assert.assertEquals((long) stock.getReserved(), 0L);
            Assert.assertEquals(new Long(stock.getStock()), stockMap.get(stock.getPhoneId()));
        }
    }
}
