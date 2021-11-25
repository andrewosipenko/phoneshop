package com.es.core.model.enums;

public enum SortOrder {
    ASCENDING("asc"), DESCENDING("desc");
    private final String columnName;

    SortOrder(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }
}
