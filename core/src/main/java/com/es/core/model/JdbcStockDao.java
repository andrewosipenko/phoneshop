package com.es.core.model;

import com.es.core.model.mappers.StockRowMapper;
import com.es.core.model.phone.Stock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JdbcStockDao implements StockDao {

    private static final String SELECT_STOCKS_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL";

    private static final String SELECT_COUNT_OF_ALL_AVAILABLE_STOCKS = "SELECT COUNT(*) FROM stocks JOIN phones ON stocks.phoneId=phones.id " +
            "WHERE (stock - reserved) > 0 AND price NOT IS NULL";

    private static final String SELECT_ALL_STOCKS_SORT_BY_BRAND_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY brand ASC LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS_SORT_BY_BRAND_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY brand DESC LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS_SORT_BY_MODEL_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY model ASC LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS_SORT_BY_MODEL_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY model DESC LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS_SORT_BY_DISPLAY_SIZE_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY displaySizeInches ASC LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS_SORT_BY_DISPLAY_SIZE_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY displaySizeInches DESC LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS_SORT_BY_PRICE_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY price ASC LIMIT ?, ?";

    private static final String SELECT_ALL_STOCKS_SORT_BY_PRICE_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE (stock - reserved) > 0 AND price NOT IS NULL ORDER BY price DESC LIMIT ?, ?";

    private static final String SELECT_STOCK_OF_PHONE_BY_PHONE_ID = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE phoneId=?";

    private static final String RESERVE_PHONES_BY_PHONE_ID = "UPDATE stocks SET reserved=reserved+? WHERE phoneId=?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private StockRowMapper stockRowMapper;

    @Override
    public List<Stock> getAllAvailableStocks() {
        return jdbcTemplate.query(SELECT_ALL_STOCKS, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_STOCKS_BY_PAGE, new Object[]{pageId - 1, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAllAvailableStocks_search(String searchFor) {
        String SELECT_ALL_STOCKS_SEARCH = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH, stockRowMapper);
    }

    @Override
    public int getCountOfAllAvailableStocks() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_OF_ALL_AVAILABLE_STOCKS, Integer.class);
    }

    @Override
    public int getCountOfAllAvailableStocks_search(String searchFor) {
        String SELECT_COUNT_OF_ALL_AVAILABLE_STOCKS_SEARCH = "SELECT COUNT(*) FROM stocks JOIN phones ON stocks.phoneId=phones.id " +
                "WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ";
        return jdbcTemplate.queryForObject(SELECT_COUNT_OF_ALL_AVAILABLE_STOCKS_SEARCH, Integer.class);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_brand_Order_asc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_BRAND_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_brand_Order_desc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_BRAND_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_model_Order_asc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_MODEL_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_model_Order_desc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_MODEL_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_displaySize_Order_asc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_DISPLAY_SIZE_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_displaySize_Order_desc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_DISPLAY_SIZE_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_price_Order_asc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_PRICE_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_price_Order_desc_ByPage(int pageId, int total) {
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SORT_BY_PRICE_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);

    }

    @Override
    public List<Stock> getAvailableStocksSortBy_brand_Order_asc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_BRAND_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY brand ASC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_BRAND_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_brand_Order_desc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_BRAND_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY brand DESC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_BRAND_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_model_Order_asc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_MODEL_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY model ASC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_MODEL_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_model_Order_desc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_BRAND_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY model DESC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_BRAND_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_displaySize_Order_asc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_DISPLAY_SIZE_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY displaySizeInches ASC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_DISPLAY_SIZE_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_displaySize_Order_desc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_DISPLAY_SIZE_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY displaySizeInches DESC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_DISPLAY_SIZE_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_price_Order_asc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_PRICE_ORDER_ASC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY price ASC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_PRICE_ORDER_ASC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public List<Stock> getAvailableStocksSortBy_price_Order_desc_ByPage_search(String searchFor, int pageId, int total) {
        String SELECT_ALL_STOCKS_SEARCH_SORT_BY_PRICE_ORDER_DESC_BY_PAGE = "SELECT phoneId, stock, reserved, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
                "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
                "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
                "positioning, imageUrl, description FROM stocks JOIN phones ON phones.id=phoneId WHERE ((stock - reserved) > 0) AND (price NOT IS NULL) AND (brand LIKE '%" + searchFor +
                "%' OR model LIKE '%" + searchFor + "%' ) ORDER BY price DESC LIMIT ?, ?";
        return jdbcTemplate.query(SELECT_ALL_STOCKS_SEARCH_SORT_BY_PRICE_ORDER_DESC_BY_PAGE, new Object[]{pageId, total}, stockRowMapper);
    }

    @Override
    public Stock loadStockOfPhoneByPhoneId(long phoneId) {
        return jdbcTemplate.queryForObject(SELECT_STOCK_OF_PHONE_BY_PHONE_ID, new Object[]{phoneId}, stockRowMapper);
    }

    @Override
    public void reservePhonesByPhoneId(long phoneId, long quantity) {
        jdbcTemplate.update(RESERVE_PHONES_BY_PHONE_ID, new Object[]{quantity, phoneId});
    }


}
