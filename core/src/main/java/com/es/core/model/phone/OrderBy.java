package com.es.core.model.phone;


public enum OrderBy {
    BRAND("brand"), BRAND_DESC("brand DESC"),
    MODEL("model"), MODEL_DESC("model DESC"),
    DISPLAY_SIZE("displaySizeInches"), DISPLAY_SIZE_DESC("displaySizeInches DESC"),
    PRICE("price"), PRICE_DESC("price DESC");

    private String sqlCommand;

    OrderBy(String sqlCommand) {
        this.sqlCommand = sqlCommand;
    }

    public String getSqlCommand() {
        return sqlCommand;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
