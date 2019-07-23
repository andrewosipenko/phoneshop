package com.es.core.model;

import com.es.core.model.phone.Stock;

import java.util.List;

public interface StockDao {
    List<Stock> getAllAvailableStocks();

    List<Stock> getAvailableStocksByPage(int pageId, int total);

    List<Stock> getAvailableStocksByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAllAvailableStocks_search(String searchFor);

    int getCountOfAllAvailableStocks();

    int getCountOfAllAvailableStocks_search(String searchFor);

    List<Stock> getAvailableStocksSortBy_brand_Order_asc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_brand_Order_desc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_model_Order_asc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_model_Order_desc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_displaySize_Order_asc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_displaySize_Order_desc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_price_Order_asc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_price_Order_desc_ByPage(int pageId, int total);

    List<Stock> getAvailableStocksSortBy_brand_Order_asc_ByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAvailableStocksSortBy_brand_Order_desc_ByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAvailableStocksSortBy_model_Order_asc_ByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAvailableStocksSortBy_model_Order_desc_ByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAvailableStocksSortBy_displaySize_Order_asc_ByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAvailableStocksSortBy_displaySize_Order_desc_ByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAvailableStocksSortBy_price_Order_asc_ByPage_search(String searchFor, int pageId, int total);

    List<Stock> getAvailableStocksSortBy_price_Order_desc_ByPage_search(String searchFor, int pageId, int total);


}
