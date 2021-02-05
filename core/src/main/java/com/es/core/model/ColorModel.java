package com.es.core.model;

import java.util.Objects;

public class ColorModel {

    private Long id;

    private String code;

    public ColorModel() {
        id = 0L;
        this.code = "code";
    }

    public ColorModel(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public ColorModel(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorModel color = (ColorModel) o;
        return code.equals(color.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
