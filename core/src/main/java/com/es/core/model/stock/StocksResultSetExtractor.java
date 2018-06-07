package com.es.core.model.stock;

import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StocksResultSetExtractor implements ResultSetExtractor<List<Stock>> {

    private Map<Long, Phone> phonesToMap;

    public StocksResultSetExtractor(List<Phone> phonesToMap){
        this.phonesToMap = phonesToMap.stream().collect(Collectors.toMap(Phone::getId, Function.identity()));
    }

    @Override
    public List<Stock> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Stock> stocks = new LinkedList<>();

        Stock stock;
        while(rs.next()){
            stock = new Stock();
            stock.setPhone(phonesToMap.get(rs.getLong("phoneId")));
            stock.setReserved(rs.getInt("reserved"));
            stock.setStock(rs.getInt("stock"));
        }

        return stocks;
    }
}
