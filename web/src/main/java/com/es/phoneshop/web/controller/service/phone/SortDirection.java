package com.es.phoneshop.web.controller.service.phone;

public enum SortDirection {
    ASC("asc"), DECS("desc");

    private String direction;

    SortDirection(String direction){
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}