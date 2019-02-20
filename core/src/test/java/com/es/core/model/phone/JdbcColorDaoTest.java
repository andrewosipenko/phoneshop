package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class JdbcColorDaoTest {

    @Resource
    private ColorDao colorDao;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void getColorSetByPhoneIdTest() {
        final long phoneId = 1001;
        Set<Color> colors = colorDao.get(phoneId);
        assertNotNull(colors);
    }

    @Rollback(true)
    @Test
    public void saveSetColorSetWithNewColorsByPhoneIdTest() {
        final long phoneId = 1001;
        int tableSizeBeforeInsert = getPhone2ColorCount();
        Set<Color> colors = new HashSet<>();
        colors.add(createNewColor());
        colorDao.save(colors, phoneId);
        int tableSizeAfterInsert = getPhone2ColorCount();
        assertThat(tableSizeBeforeInsert, is(tableSizeAfterInsert - colors.size()));
    }

//    @Rollback(true)
//    @Test
//    public void saveSetColorSetWithExistingColorsByPhoneIdTest() {
//        final long phoneId = 1001;
//        int tableSizeBeforeInsert = getPhone2ColorCount();
//        Set<Color> colors = new HashSet<>();
//        colors.add(createExistingColor());
//        colorDao.save(colors, phoneId);
//        int tableSizeAfterInsert = getPhone2ColorCount();
//        assertThat(tableSizeBeforeInsert, is(tableSizeAfterInsert));
//    }

    @Rollback(true)
    @Test
    public void saveNewColorTest() {
        int tableSizeBeforeInsert = getColorsCount();
        colorDao.save(createNewColor());
        int tableSizeAfterInsert = getColorsCount();
        assertThat(tableSizeAfterInsert, is(tableSizeBeforeInsert + 1));
    }

    @Rollback(true)
    @Test
    public void saveExistingColorTest() {
        int tableSizeBeforeInsert = getColorsCount();
        colorDao.save(createExistingColor());
        int tableSizeAfterInsert = getColorsCount();
        assertThat(tableSizeAfterInsert, is(tableSizeBeforeInsert));
    }

    public Color createNewColor() {
        Color color =  new Color();
        color.setCode("purple");
        return color;
    }

    public Color createExistingColor() {
        Color color =  new Color();
        color.setCode("White");
        return color;
    }

    public int getColorsCount() {
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from colors", Integer.class);
    }

    public int getPhone2ColorCount() {
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phone2color", Integer.class);
    }
}