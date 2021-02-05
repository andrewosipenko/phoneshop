package com.es.core.dao;

import com.es.core.model.PhoneModel;
import com.es.core.model.ColorModel;
import com.es.core.model.extractor.PhoneExtractor;
import com.es.core.model.ProductListPageParametersModel;
import com.es.core.util.FindAndSortUtil;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
@PropertySource("classpath:properties/application.properties")
public class JdbcPhoneDao implements PhoneDao {

    private static final String PHONES_WITH_COLORS_QUERY = "select * from phones join stocks on stocks.phoneId=phones.id " +
                                                           "left join phone2color on phones.id=phone2color.phoneId " +
                                                           "left join colors on phone2color.colorId=colors.id " +
                                                           "where phones.price is not null and stocks.stock>0 ";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<PhoneModel> get(final Long key) {
        return  getPhoneListWithColors().stream()
                                        .filter(phone -> phone.getId().equals(key))
                                        .findAny();
    }

    public void save(final PhoneModel phoneToSave) {
        List<PhoneModel> phoneList = getPhoneListWithColors();
        if (phoneList.contains(phoneToSave)) {
            jdbcTemplate.update("update phones set (phones.price) = (?)", phoneToSave.getPrice());
            int index = phoneList.indexOf(phoneToSave);
            updateColorsDataBases(phoneToSave, phoneList.get(index));
        } else {
            jdbcTemplate.update("insert into phones (brand, model, price, imageUrl) values (?, ?, ?, ?)",
                    phoneToSave.getBrand(), phoneToSave.getModel(), phoneToSave.getPrice(), phoneToSave.getImageUrl());
            insertColorsToNewPhoneIfExists(phoneToSave);
        }
    }

    private List<PhoneModel> getPhoneListWithColors() {
        return jdbcTemplate.query(PHONES_WITH_COLORS_QUERY, new PhoneExtractor());
    }

    private void insertColorsToNewPhoneIfExists(PhoneModel phoneToSave) {
        for (ColorModel currentColor : phoneToSave.getColors()) {
            jdbcTemplate.update("insert into phone2color values (?, ?)", phoneToSave.getId(), currentColor.getId());
        }
    }

    private void updateColorsDataBases(PhoneModel phoneToSave, PhoneModel savedPhone) {
        deleteUnusedColorsToSavedPhone(savedPhone, phoneToSave);
        insertNewColorsToSavedPhone(savedPhone, phoneToSave);
    }

    private void deleteUnusedColorsToSavedPhone(PhoneModel savedPhone, PhoneModel phoneToSave) {
        for (ColorModel currentColor : savedPhone.getColors()) {
            if (!phoneToSave.getColors().contains(currentColor)) {
                jdbcTemplate.update("delete from phone2color where colorId = ? and phoneId = ?", currentColor.getId(), phoneToSave.getId());
            }
        }
    }

    private void insertNewColorsToSavedPhone(PhoneModel savedPhone, PhoneModel phoneToSave) {
        for (ColorModel currentColor : phoneToSave.getColors()) {
            if (!savedPhone.getColors().contains(currentColor)) {
                jdbcTemplate.update("insert into phone2color values (?, ?)", phoneToSave.getId(), currentColor.getId());
            }
        }
    }

    public List<PhoneModel> findAll(int limit, int offset) {
        return jdbcTemplate.query(PHONES_WITH_COLORS_QUERY, new PhoneExtractor());
    }

    public List<PhoneModel> searchFor(ProductListPageParametersModel parametersModel) {
        List<PhoneModel> phoneList;
        FindAndSortUtil findAndSortUtil = new FindAndSortUtil(jdbcTemplate);
        if (parametersModel == null) {
            return getPhoneListWithColors();
        } else if (parametersModel.allParametersFilled()) {
            return findAndSortUtil.findByAllParameters(parametersModel);
        } else if (!parametersModel.emptyQuery()) {
            return searchForPhonesByNameQuery(parametersModel);
        }
        phoneList = findAndSortUtil.sortProductsList(parametersModel.getFieldToSort(), parametersModel.getOrderToSort(), parametersModel.getLimit(),
                parametersModel.getOffset());
        return phoneList;
    }

    public List<PhoneModel> searchForPhonesByNameQuery(ProductListPageParametersModel parametersModel) {
        String phoneNameQuery = parametersModel.getPhoneNameQuery();
        FindAndSortUtil findAndSortUtil = new FindAndSortUtil(jdbcTemplate);
        return findAndSortUtil.findByAllWordsInQuery(parametersModel, phoneNameQuery);
    }

}
