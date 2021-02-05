package com.es.core.util;

import com.es.core.model.PhoneModel;
import com.es.core.model.ProductListPageParametersModel;
import com.es.core.model.extractor.PhoneExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAndSortUtil {

    private static final String PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART = "select * from (select * from phones join stocks on stocks.phoneId=id where " +
            "phones.price is not null and stocks.stock > 0 order by ";

    private static final String PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART = " limit ? offset ? ) as phoneWithPriceStock " +
            "left join phone2color as p2c on phoneWithPriceStock.id=p2c.phoneId " +
            "left join colors on p2c.colorId=colors.id" ;

    private static String SEARCHING_PHONES_BY_ALL_PARAMS_NAME_FIRST_PART = "select * from (select * from phones join stocks on stocks.phoneId=id where " +
            " (phones.price is not null and stocks.stock > 0 and ((lower(phones.model) like ? or lower(phones.description) like ?))) order by ";

    private static String SEARCHING_PHONES_BY_ALL_PARAMS_NAME_SECOND_PART =  " limit ? offset ? ) as phoneWithPriceStock " +
            "left join phone2color as p2c on phoneWithPriceStock.id=p2c.phoneId " +
            "left join colors on p2c.colorId=colors.id ";

    private JdbcTemplate jdbcTempl;

    @Autowired
    public FindAndSortUtil(JdbcTemplate jdbcTemplate) {
        this.jdbcTempl = jdbcTemplate;
    }

    public List<PhoneModel> sortProductsList(String fieldToSort, String orderToSort, int limit, int offset) {
        return fieldToSort.equals("displaySizeInches") ?
                sortingOnSize(orderToSort, limit, offset) :
                fieldToSort.equals("brand") ?
                        sortingOnBrand(orderToSort, limit, offset) :
                        fieldToSort.equals("model") ?
                                sortingOnModel(orderToSort, limit, offset) :
                                sortingOnPrice(orderToSort, limit, offset);
    }

    private List<PhoneModel> resultsOfSorting(String query, int limit, int offset) {
        return jdbcTempl.query(query, new Object[]{limit, offset}, new PhoneExtractor());
    }

    private List<PhoneModel> sortingOnSize(String order, int limit, int offset) {
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.displaySizeInches " + order + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    private List<PhoneModel> sortingOnBrand(String order, int limit, int offset) {
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.brand " + order + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    private List<PhoneModel> sortingOnModel(String order, int limit, int offset) {
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.model " + order + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    private List<PhoneModel> sortingOnPrice(String order, int limit, int offset) {
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.price " + order + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    public List<PhoneModel> findByAllParameters(ProductListPageParametersModel parametersModel) {
        String order = parametersModel.getOrderToSort();
        String field = parametersModel.getFieldToSort();
        return findByAllWordsInQuery(parametersModel, SEARCHING_PHONES_BY_ALL_PARAMS_NAME_FIRST_PART + " "
                + "phones.".concat(field) + " " + order + SEARCHING_PHONES_BY_ALL_PARAMS_NAME_SECOND_PART);
    }

    public List<PhoneModel> findByAllWordsInQuery(ProductListPageParametersModel parametersModel, String queryForDataBase) {
        List<String> queryItems = Arrays.asList(parametersModel.getPhoneNameQuery().split(" "));
        List<PhoneModel> phoneModels = new ArrayList<>();
        if(queryItems.size() == 0) {
            phoneModels.addAll(jdbcTempl.query(queryForDataBase, new Object[]{"%%", "%%", parametersModel.getLimit(), parametersModel.getOffset()},
                    new PhoneExtractor()));
        } else {
            for (String query : queryItems) {
                phoneModels.addAll(jdbcTempl.query(queryForDataBase, new Object[]{
                                "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%", parametersModel.getLimit(), parametersModel.getOffset()},
                        new PhoneExtractor()));
            }
        }
        return phoneModels;
    }

    @Resource
    public void setJdbcTempl(JdbcTemplate jdbcTempl) {
        this.jdbcTempl = jdbcTempl;
    }

}
