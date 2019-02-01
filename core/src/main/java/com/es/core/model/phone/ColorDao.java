package com.es.core.model.phone;

import java.util.Optional;
import java.util.Set;

public interface ColorDao {

    void saveColor(Color color);
    Set<Color> getPhoneColors(final Long key);
    void addColorsToPhone(final Set<Color> colors, final Long phoneId);
    Optional<Color> get(final Long phoneId);

}
