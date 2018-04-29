package com.es.core.validation.validator;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.exception.EmptyOrderItemsException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class OrderQuantityValidator {
    @Resource
    private PhoneDao phoneDao;

    public boolean isValidOrderQuantity(Order order){
        Map<Long, Stock> stocksMap = convertListStocksToMap(phoneDao.getPhonesStocks(getPhonesFromOrder(order)));
        List<OrderItem> orderItems = order.getOrderItems();
        if(orderItems.size() == 0){
            throw new EmptyOrderItemsException();
        }
        for(OrderItem orderItem : orderItems){
            Long phoneId = orderItem.getPhone().getId();
            Stock stock = stocksMap.get(phoneId);
            if(orderItem.getQuantity() > stock.getStock()){
                return false;
            }
        }
        return true;
    }

    private List<Phone> getPhonesFromOrder(Order order){
        List<Phone> phones = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            phones.add(orderItem.getPhone());
        }
        return phones;
    }

    private Map<Long, Stock> convertListStocksToMap(List<Stock> stocks){
        return stocks.stream().collect(Collectors.toMap(Stock -> Stock.getPhone().getId(), Stock -> Stock));
    }
}
