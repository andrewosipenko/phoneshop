package com.es.phoneshop.core.phone.dao.util;

public enum SortBy {
    BRAND("brand"), MODEL("model"), DISPLAY_SIZE("displaySizeInches"), PRICE("price"),
    BRAND_DESC("brand DESC"), MODEL_DESC("model DESC"), DISPLAY_SIZE_DESC("displaySizeInches DESC"), PRICE_DESC("price DESC");

    private String sql;

    SortBy(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
