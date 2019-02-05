package com.es.core.model.phone;

import java.util.Optional;
import java.util.Set;

public interface ColorDao {
    void saveColor(Color color);
    Set<Color> getPhoneColors( Long key);
    void addColorsToPhone(Set<Color> colors, Long phoneId);
    Optional<Color> get(Long phoneId);
}
