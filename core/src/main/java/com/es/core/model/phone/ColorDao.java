package com.es.core.model.phone;

import java.util.List;
import java.util.Set;

public interface ColorDao {
    Set<Color> get(Long key);
    void save(Color color);
    void save(Set<Color> colors, Long key);
    List<Color> findAll(int offset, int limit);
}
