package com.es.core.model.phone;

import java.util.Objects;

public class Color {
    private Long id;
    private String code;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != Color.class)
            return false;
        Color color2 = (Color) obj;
        return Objects.equals(id, color2.id) && Objects.equals(code, color2.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
