package com.es.phoneshop.web.services;

public enum  DirectionEnum {
    ASC("asc"), DESC("desc");

    private String direction;

    DirectionEnum(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
