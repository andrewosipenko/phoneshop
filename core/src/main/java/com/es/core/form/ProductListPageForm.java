package com.es.core.form;

public class ProductListPageForm {

    private String phoneNameQuery;

    private String fieldToSort;

    private String orderToSort;

    private Integer page;

    public ProductListPageForm() {
        this.fieldToSort = "";
        this.orderToSort = "";
        this.phoneNameQuery = "";
        this.page = 1;
    }

    public ProductListPageForm(String phoneNameQuery, String fieldToSort, String orderToSort, Integer page) {
        this.fieldToSort = fieldToSort;
        this.orderToSort = orderToSort;
        this.page = page;
        if (phoneNameQuery != null) {
            this.phoneNameQuery = phoneNameQuery;
        } else {
            this.phoneNameQuery = "";
        }
    }

    public String getFieldToSort() {
        return fieldToSort;
    }

    public String getOrderToSort() {
        return orderToSort;
    }

    public String getPhoneNameQuery() {
        return phoneNameQuery;
    }

    public Integer getPage() {
        return page;
    }
}
