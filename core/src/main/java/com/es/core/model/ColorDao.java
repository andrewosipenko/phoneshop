package com.es.core.model;

import com.es.core.model.phone.Color;

import java.util.Set;

public interface ColorDao {
    Set<Color> loadColorsOfPhoneByID(long id);
}
