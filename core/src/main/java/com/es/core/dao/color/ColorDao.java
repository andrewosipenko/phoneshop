package com.es.core.dao.color;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;

import java.util.Set;

public interface ColorDao {
    void savePhoneColors(Phone phone);
    Set<Color> findColorsToPhone(Long key);
}
