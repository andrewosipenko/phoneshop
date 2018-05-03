package com.es.core.service.stock;

import com.es.core.dao.stockDao.StockDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.exception.OutOfStockException;
import com.es.core.service.phone.PhoneService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class StockServiceIntTest {
    @InjectMocks
    private StockService stockService;

    @Mock
    private StockDao stockDao;
    @Mock
    private PhoneService phoneService;

    private final long PHONE_WITH_STOCK_ID_1 = 1001L;
    private final long PHONE_WITH_STOCK_ID_2 = 1004L;
    private Phone phone1;
    private Phone phone2;
    private List<Phone> phonesWithExistingStocks;

    private final long AVAILABLE_STOCK = 10L;
    private final long RESERVED = 5L;
    private final long UNAVAILABLE_STOCK = 0L;
    private final long AVAILABLE_QUANTITY = 5L;
    private final long UNAVAILABLE_QUANTITY = 100L;



    @Before
    public void initObjects(){
        MockitoAnnotations.initMocks(this);
        phone1 = new Phone();
        phone2 = new Phone();
        phone1.setId(PHONE_WITH_STOCK_ID_1);
        phone2.setId(PHONE_WITH_STOCK_ID_2);
        phonesWithExistingStocks = Arrays.asList(phone1, phone2);

    }

    @Test
    public void testGetPhonesStocks(){
       stockService.getPhonesStocks(phonesWithExistingStocks);
        for(Phone phone : phonesWithExistingStocks){
            Mockito.verify(stockDao, Mockito.times(1)).getStockByPhoneId(phone.getId());
        }
    }

    @Test
    public void updateValidStocks() throws OutOfStockException{
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(AVAILABLE_QUANTITY);
        orderItem.setPhone(phone1);
        order.setOrderItems(Arrays.asList(orderItem));
        Stock stock = new Stock();
        stock.setStock(AVAILABLE_STOCK);
        stock.setPhone(phone1);
        stock.setReserved(RESERVED);

        Mockito.when(phoneService.getPhonesFromOrder(order)).thenReturn(Arrays.asList(phone1));
        Mockito.when(stockDao.getStockByPhoneId(PHONE_WITH_STOCK_ID_1)).thenReturn(stock);
        stockService.updateStocks(order);
        Mockito.verify(stockDao).updateStocks(Arrays.asList(stock));
        Assert.assertTrue(stock.getStock().equals(AVAILABLE_STOCK - AVAILABLE_QUANTITY));
        Assert.assertTrue(stock.getReserved().equals(RESERVED + AVAILABLE_QUANTITY));
    }

    @Test(expected = OutOfStockException.class)
    public void updateInvalidStocks() throws OutOfStockException{
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(UNAVAILABLE_QUANTITY);
        orderItem.setPhone(phone1);
        order.setOrderItems(Arrays.asList(orderItem));
        Stock stock = new Stock();
        stock.setStock(UNAVAILABLE_STOCK);
        stock.setPhone(phone1);

        Mockito.when(phoneService.getPhonesFromOrder(order)).thenReturn(Arrays.asList(phone1));
        Mockito.when(stockDao.getStockByPhoneId(PHONE_WITH_STOCK_ID_1)).thenReturn(stock);

        stockService.updateStocks(order);
    }
}
