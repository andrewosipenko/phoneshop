package com.es.core.service.stock;

import com.es.core.dao.stock.StockDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {
    private static final long ID = 1L;
    private static final long RESERVED = 1L;
    private static final long STOCK = 4L;

    @InjectMocks
    private StockService stockService = new StockServiceImpl();

    @Mock
    private StockDao stockDao;

    @Before
    public void setUp() throws Exception {
        when(stockService.findPhoneStock(ID)).thenReturn(new Stock(new Phone(), STOCK, RESERVED));
    }

    @Test
    public void shouldReturnPhoneStock() {
        stockService.findPhoneStock(ID);

        verify(stockDao, times(1)).findPhoneStock(ID);
    }

    @Test
    public void shouldAddReserved() {
        stockService.addReserved(ID, RESERVED);

        verify(stockDao, times(1)).updateStock(ID, STOCK - RESERVED, RESERVED + RESERVED);
    }

    @Test
    public void shouldReplaceReservedToStock() {
        stockService.replaceReservedToStock(ID, RESERVED);

        verify(stockDao, times(1)).updateStock(ID, STOCK + RESERVED, RESERVED - RESERVED);
    }

    @Test
    public void shouldDeleteReserved() {
        stockService.deleteReserved(ID, RESERVED);

        verify(stockDao, times(1)).updateStock(ID, STOCK, RESERVED - RESERVED);
    }
}