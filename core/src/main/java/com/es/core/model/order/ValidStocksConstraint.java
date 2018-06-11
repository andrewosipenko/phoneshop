package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidStocksConstraint implements ConstraintValidator<ValidStocks, List<OrderItem>> {

    @Resource
    private StockDao stockDao;

    private String message;

    public void initialize(ValidStocks constraint) {
        this.message = constraint.message();
    }

    public boolean isValid(List<OrderItem> value, ConstraintValidatorContext context) {
        Map<Phone, Stock> stocks = stockDao.getStocksForPhones(value.stream().map(OrderItem::getPhone).collect(Collectors.toList()));

        OrderItem item;
        boolean isListValid = true;
        boolean isValid;
        for(int i = 0 ; i < value.size(); i++){
            item = value.get(i);
            isValid = isOutOfStock(i, item, stocks.get(item.getPhone()), context);
            isListValid = isListValid && isValid;
        }
        return isListValid;
    }

    private boolean isOutOfStock(int index, OrderItem orderItem, Stock stock, ConstraintValidatorContext context){
        boolean isValid = stock.getStock() - stock.getReserved() - orderItem.getQuantity() > 0;

        if(!isValid){
            context.buildConstraintViolationWithTemplate(message)
                    .addBeanNode()
                        .inIterable()
                        .atIndex(index)
                    .addConstraintViolation();
        }

        return isValid;
    }

}
