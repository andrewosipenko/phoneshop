package com.es.core.service.stock;

import com.es.core.dao.stock.StockDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {
    @InjectMocks
    private StockService stockService = new StockServiceImpl();

    @Mock
    private StockDao stockDao;

    @Test
    public void shouldReturnPhoneStock() {
        stockService.findPhoneStock(1L);

        verify(stockDao, times(1)).findPhoneQuantity(1L);
    }
}