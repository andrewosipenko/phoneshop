package com.es.phoneshop.web.services;

public enum SortEnum {
    BRAND("brand"), MODEL("model"), PRICE("price"), DISPLAY_SIZE("displaySizeInches");

    private String column;

    SortEnum(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
