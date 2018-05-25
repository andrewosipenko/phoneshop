package com.es.core.model.phone;

public enum OrderEnum {
    BRAND("brand"), BRAND_DESC("brand DESC"),
    MODEL("model"), MODEL_DESC("model DESC"),
    DISPLAY_SIZE("displaySizeInches"), DISPLAY_SIZE_DESC("displaySizeInches DESC"),
    PRICE("price"), PRICE_DESC("price DESC");

    private String sql;

    OrderEnum(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
