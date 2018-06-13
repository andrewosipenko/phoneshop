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
        boolean isValid = true;
        for(int i = 0 ; i < value.size(); i++){
            item = value.get(i);
            if(isOutOfStock(item, stocks.get(item.getPhone()))) {
                addConstraintViolation(i, context);
                isValid = false;
            }
        }
        return isValid;
    }

    private void addConstraintViolation(int index, ConstraintValidatorContext context){
        context.buildConstraintViolationWithTemplate(message)
                .addBeanNode()
                    .inIterable()
                    .atIndex(index)
                .addConstraintViolation();
    }

    private boolean isOutOfStock(OrderItem orderItem, Stock stock){
        return stock.getStock() - stock.getReserved() - orderItem.getQuantity() <= 0;
    }

}
