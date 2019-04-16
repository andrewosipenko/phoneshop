package com.es.core.service.stock;

import com.es.core.dao.stock.StockDao;
import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {
    private final String ERROR_MESSAGE = "Some phone are out of stocks. So they were removed from your cart";

    @Resource
    private StockDao stockDao;
    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;

    @Override
    public List<Stock> getPhonesStocks(List<Phone> phones) {
        List<Stock> stocks = new ArrayList<>();
        for (Phone phone : phones) {
            Stock stock = stockDao.get(phone.getId()).get();
            stocks.add(stock);
        }
        return stocks;
    }

    @Override
    public void updateStocks(Order order, boolean flag) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<Phone> phones = phoneService.getPhoneListFromOrder(order);
        List<Stock> stocks = getPhonesStocks(phones);
        Map<Long, Stock> stockMap = convertListStocksToMap(stocks);
        for (OrderItem orderItem : orderItems) {
            Long phoneId = orderItem.getPhone().getId();
            Long quantity = orderItem.getQuantity();
            Stock stock = stockMap.get(phoneId);
            Long oldStock = stock.getStock();
            Long oldReserved = stock.getReserved();
            if (oldStock < quantity) {
                throw new OutOfStockException(ERROR_MESSAGE);
            } else {
                Long newStock = (flag) ? oldStock - quantity : oldStock + quantity;
                stock.setStock(newStock);
                Long newReserved = (flag) ? oldReserved + quantity : oldReserved - quantity;
                stock.setReserved(newReserved);
            }
        }
        stockDao.update(new ArrayList<>(stockMap.values()));
    }

    @Override
    public void reduceReserved(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<Phone> phones = phoneService.getPhoneListFromOrder(order);
        List<Stock> stocks = getPhonesStocks(phones);
        Map<Long, Stock> stockMap = convertListStocksToMap(stocks);
        for (OrderItem orderItem : orderItems) {
            Long phoneId = orderItem.getPhone().getId();
            Long quantity = orderItem.getQuantity();
            Stock stock = stockMap.get(phoneId);
            Long oldReserved = stock.getReserved();
            Long newReserved = oldReserved - quantity;
            stock.setReserved(newReserved);
        }
        stockDao.update(new ArrayList<>(stockMap.values()));
    }

    public Map<Long, Stock> convertListStocksToMap(List<Stock> stocks) {
        return stocks.stream().collect(Collectors.toMap(Stock -> Stock.getPhone().getId(), Stock -> Stock));
    }
}
