package com.es.phoneshop.web.bean.pagination;

import com.es.core.model.phone.Phone;

import java.util.List;

public class CurrentPhonePage {
    private Long totalCount;
    private Integer firstShownPageNumber;
    private Integer lastShownPageNumber;
    private Integer lastPageNumber;
    private Integer currentPageNumber;
    private List<Phone> currentPagePhoneList;

    public CurrentPhonePage(Long totalCount, Integer firstShownPageNumber,
                            Integer lastShownPageNumber, Integer lastPageNumber,
                            Integer currentPageNumber, List<Phone> currentPagePhoneList) {
        this.totalCount = totalCount;
        this.firstShownPageNumber = firstShownPageNumber;
        this.lastShownPageNumber = lastShownPageNumber;
        this.lastPageNumber = lastPageNumber;
        this.currentPageNumber = currentPageNumber;
        this.currentPagePhoneList = currentPagePhoneList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(Integer lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public List<Phone> getCurrentPagePhoneList() {
        return currentPagePhoneList;
    }

    public void setCurrentPagePhoneList(List<Phone> currentPagePhoneList) {
        this.currentPagePhoneList = currentPagePhoneList;
    }

    public Integer getFirstShownPageNumber() {
        return firstShownPageNumber;
    }

    public void setFirstShownPageNumber(Integer firstShownPageNumber) {
        this.firstShownPageNumber = firstShownPageNumber;
    }

    public Integer getLastShownPageNumber() {
        return lastShownPageNumber;
    }

    public void setLastShownPageNumber(Integer lastShownPageNumber) {
        this.lastShownPageNumber = lastShownPageNumber;
    }
}
