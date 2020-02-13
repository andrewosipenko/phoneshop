package com.es.phoneshop.web.page;

import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.page.Pagination;

import java.util.List;

public class PhonePage {
    private List<Phone> phoneList;
    private Pagination pagination;

    public PhonePage() {
    }

    public PhonePage(List<Phone> phoneList, Pagination pagination) {
        this.phoneList = phoneList;
        this.pagination = pagination;
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
