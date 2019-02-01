package com.es.core.dao.color;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Repository
public class JdbcColorDao implements ColorDao {
    private final static String REGEX_TO_ADD_ARRAY_PARAMETER = "arrayParameter";

    private final static String QUERY_TO_DELETE_OLD_PHONE_COLORS =
            "delete from phone2color where phoneId = ? and colorId in (arrayParameter)";
    private final static String QUERY_TO_MERGE_NEW_COLOR =
            "merge into phone2color (phoneId, colorId) key(phoneId, colorId) values(?, ?)";
    private final static String QUERY_FOR_FIND_COLORS_BY_PHONE_ID =
            "select * from colors where id IN (select colorId from phone2color where phoneId = ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void savePhoneColors(Phone phone) {
        Set<Color> oldColors = findColorsToPhone(phone.getId());
        Set<Color> newColors = phone.getColors();

        long[] colorsIdToDelete = oldColors.stream()
                .filter(oldColor -> newColors.stream()
                        .noneMatch(oldColor::equals))
                .mapToLong(Color::getId).toArray();

        String qeryToDeleteOldColors = addSetParametersToQuery(colorsIdToDelete, QUERY_TO_DELETE_OLD_PHONE_COLORS);
        jdbcTemplate.update(qeryToDeleteOldColors, phone.getId());

        for (Color color : newColors) {
            jdbcTemplate.update(QUERY_TO_MERGE_NEW_COLOR, phone.getId(), color.getId());
        }
    }

    @Override
    public Set<Color> findColorsToPhone(Long key) {
        return new HashSet<>(jdbcTemplate
                .query(QUERY_FOR_FIND_COLORS_BY_PHONE_ID, new BeanPropertyRowMapper<>(Color.class), key));
    }

    private String addSetParametersToQuery(long[] arrayParameters, String query) {
        String stringParameter = Arrays.toString(arrayParameters);
        stringParameter = stringParameter.substring(1, stringParameter.length() - 1);

        return query.replace(REGEX_TO_ADD_ARRAY_PARAMETER, stringParameter);
    }
}
