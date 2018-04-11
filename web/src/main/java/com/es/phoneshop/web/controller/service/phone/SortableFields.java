package com.es.phoneshop.web.controller.service.phone;

public enum SortableFields {
    BRAND("brand"), MODEL("model"), PRICE("price"), DISPLAY_SIZE("displaySizeInches");

    private String name;

    SortableFields(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
