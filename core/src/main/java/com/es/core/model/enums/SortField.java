package com.es.core.model.enums;

public enum SortField {
    BRAND("brand"),
    MODEL("model"),
    DISPLAY_SIZE("displaySizeInches"),
    PRICE("price");
    private final String columnName;

    SortField(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }
}
