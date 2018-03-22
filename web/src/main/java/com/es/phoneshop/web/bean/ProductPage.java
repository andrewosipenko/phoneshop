package com.es.phoneshop.web.bean;


import com.es.core.model.phone.Phone;

import java.util.List;

public class ProductPage {

    private long count;

    private List<Phone> phoneList;

    private Pagination pagination;

    public ProductPage() {
    }

    public ProductPage(long count, List<Phone> phoneList, Pagination pagination) {
        this.count = count;
        this.phoneList = phoneList;
        this.pagination = pagination;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
