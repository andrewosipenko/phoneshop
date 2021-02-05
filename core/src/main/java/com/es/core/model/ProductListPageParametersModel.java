package com.es.core.model;

import org.springframework.beans.factory.annotation.Value;

public class ProductListPageParametersModel {

    private String phoneNameQuery;

    private String fieldToSort;

    private String orderToSort;

    private Integer page;

    @Value("${phones.limit}")
    private Integer limit;

    private Integer offset;

    public ProductListPageParametersModel() {
        this.fieldToSort = "";
        this.orderToSort = "";
        this.phoneNameQuery = "";
        this.page = 1;
        this.limit = 0;
        this.offset = 0;
    }

    public boolean emptyQuery() {
        return phoneNameQuery == null || phoneNameQuery.equals("");
    }

    public boolean allParametersFilled() {
        return !phoneNameQuery.equals("") && !fieldToSort.equals("") && !orderToSort.equals("");
    }

    public String getPhoneNameQuery() {
        return phoneNameQuery;
    }

    public String getFieldToSort() {
        return fieldToSort;
    }

    public String getOrderToSort() {
        return orderToSort;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setPhoneNameQuery(String phoneNameQuery) {
        this.phoneNameQuery = phoneNameQuery;
    }

    public void setFieldToSort(String fieldToSort) {
        this.fieldToSort = fieldToSort;
    }

    public void setOrderToSort(String orderToSort) {
        this.orderToSort = orderToSort;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
