package com.es.core.model;

import com.es.core.model.phone.Phone;

import java.util.List;

public class ProductPage {

    private List<Phone> phoneList;
    private String sortBy;
    private String query;
    private SqlOrderByKeyword sqlOrderByKeyword;
    private Integer totalNumOFPages;
    private Integer currentPage;

    public ProductPage() {

    }

    public ProductPage(String sortBy, String query, SqlOrderByKeyword sqlOrderByKeyword, Integer currentPage) {
        this.sortBy = sortBy;
        this.sqlOrderByKeyword = sqlOrderByKeyword;
        this.query = query;
        this.currentPage = currentPage;
    }

    public ProductPage(List<Phone> phoneList, String sortBy, SqlOrderByKeyword sqlOrderByKeyword, String query, Integer totalNumOFPages, Integer currentPage) {
        this.phoneList = phoneList;
        this.sortBy = sortBy;
        this.sqlOrderByKeyword = sqlOrderByKeyword;
        this.query = query;
        this.totalNumOFPages = totalNumOFPages;
        this.currentPage = currentPage;
    }

    public ProductPage(String orderBy, SqlOrderByKeyword sqlOrderByKeyword, Integer page) {
        this.sortBy = orderBy;
        this.sqlOrderByKeyword = sqlOrderByKeyword;
        this.currentPage = page;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SqlOrderByKeyword getSqlOrderByKeyword() {
        return sqlOrderByKeyword;
    }

    public void setSqlOrderByKeyword(SqlOrderByKeyword sqlOrderByKeyword) {
        this.sqlOrderByKeyword = sqlOrderByKeyword;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getTotalNumOFPages() {
        return totalNumOFPages;
    }

    public void setTotalNumOFPages(Integer totalNumOFPages) {
        this.totalNumOFPages = totalNumOFPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
