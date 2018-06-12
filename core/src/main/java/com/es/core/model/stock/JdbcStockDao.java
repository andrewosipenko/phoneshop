package com.es.core.model.stock;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JdbcStockDao implements StockDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String SQL_STOCKS_QUERY = "SELECT * FROM stocks WHERE stocks.phoneId IN (%s)";

    private final String SQL_DECREASE_STOCK_QUERY = "UPDATE stocks SET stocks.stock = stocks.stock-? WHERE stocks.phoneId = ?";

    private List<Stock> queryStocks(List<Phone> phones){
        String inParameter = String.join(
                ", ",
                phones.stream().map((p)->p.getId().toString()).collect(Collectors.toList())
        );
        return jdbcTemplate.query(
                String.format(SQL_STOCKS_QUERY, inParameter),
                new StocksResultSetExtractor(phones)
        );
    }

    private int[] updateStock(List<Object[]> batchArgs){
        return jdbcTemplate.batchUpdate(SQL_DECREASE_STOCK_QUERY, batchArgs);
    }


    private List<Object[]> obtainBatchArgs(List<OrderItem> items){
        return items.stream()
                .map((item)->new Object[]{item.getQuantity(), item.getPhone().getId()})
                .collect(Collectors.toList());
    }

    @Override
    public Map<Phone, Stock> getStocksForPhones(List<Phone> phones) {
       return queryStocks(phones)
               .stream()
               .collect(Collectors.toMap(Stock::getPhone, Function.identity()));
    }

    @Override
    public void decreaseStocks(Order order) {
        updateStock(obtainBatchArgs(order.getOrderItems()));
    }
}
