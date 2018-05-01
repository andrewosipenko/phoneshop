package com.es.core.service.stock;

import com.es.core.dao.stockDao.StockDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.exception.OutOfStockException;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {
    @Resource
    private PhoneService phoneService;
    @Resource
    private StockDao stockDao;

    public Map<Long, Stock> convertListStocksToMap(List<Stock> stocks){
        return stocks.stream().collect(Collectors.toMap(Stock -> Stock.getPhone().getId(), Stock -> Stock));
    }

    public List<Stock> convertStockMapToList(Map<Long, Stock> stockMap){
        return new ArrayList<>(stockMap.values());
    }

    public void updateStocks(Order order) throws OutOfStockException {
        List<OrderItem> orderItems = order.getOrderItems();
        List<Phone> phones = phoneService.getPhonesFromOrder(order);
        Map<Long, Stock> stockMap = convertListStocksToMap(stockDao.getPhonesStocks(phones));
        for(OrderItem orderItem : orderItems){
            Long quantity = orderItem.getQuantity();
            Long phoneId = orderItem.getPhone().getId();
            Stock stock = stockMap.get(phoneId);
            Long oldStock = stock.getStock();
            Long oldReserved = stock.getReserved();
            if(oldStock < quantity){
                throw new OutOfStockException("Some phones are out of stock\n" +
                        "so they were removed from your cart");
            }
            else{
                stock.setStock(oldStock - quantity);
                stock.setReserved(oldReserved + quantity);
            }
        }
        stockDao.updateStocks(convertStockMapToList(stockMap));
    }
}
