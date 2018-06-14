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

    
    private final static String SQL_STOCKS_QUERY = "SELECT * FROM stocks WHERE stocks.phoneId IN (%s)";

    private final static String SQL_CHANGE_STOCK_RESERVED =
            "UPDATE stocks " + 
            "SET stocks.reserved = stocks.reserved+? " +
            "WHERE stocks.phoneId = ?";

    private final static String SQL_APPLY_RESERVE =
            "UPDATE stocks " +
            "SET stocks.stock = stocks.stock - ?, stocks.reserved = stocks.reserved - ? " +
            "WHERE stocks.phoneId = ?";

    
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

    private List<Object[]> obtainBatchArgs(Order order, BatchArgsFormatter formatter){
        return order.getOrderItems().stream()
                .map(formatter::format)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Phone, Stock> getStocksForPhones(List<Phone> phones) {
       return queryStocks(phones)
               .stream()
               .collect(Collectors.toMap(Stock::getPhone, Function.identity()));
    }

    @Override
    public void reserveStocks(Order order) {
        jdbcTemplate.batchUpdate(
                SQL_CHANGE_STOCK_RESERVED,
                obtainBatchArgs(order, BatchArgsFormatter.RESERVE)
        );
    }

    @Override
    public void rejectReserved(Order order) {
        jdbcTemplate.batchUpdate(
                SQL_CHANGE_STOCK_RESERVED,
                obtainBatchArgs(order, BatchArgsFormatter.REJECT)
        );
    }

    @Override
    public void applyReserved(Order order) {
        jdbcTemplate.batchUpdate(
                SQL_APPLY_RESERVE,
                obtainBatchArgs(order, BatchArgsFormatter.APPLY)
        );
    }


    private enum BatchArgsFormatter{
        RESERVE{
            @Override
            Object[] format(OrderItem item) {
                return new Object[]{item.getQuantity(), item.getPhone().getId()};
            }
        },
        APPLY{
            @Override
            Object[] format(OrderItem item) {
                return new Object[]{item.getQuantity(), item.getQuantity(), item.getPhone().getId()};
            }
        },
        REJECT{
            @Override
            Object[] format(OrderItem item) {
                return new Object[]{-item.getQuantity(), item.getPhone().getId()};
            }
        };

        abstract Object[] format(OrderItem item);
    }
}
