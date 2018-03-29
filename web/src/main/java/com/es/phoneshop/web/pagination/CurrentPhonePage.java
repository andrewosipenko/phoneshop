package com.es.phoneshop.web.pagination;

import com.es.core.model.phone.Phone;

import java.util.List;

public class CurrentPhonePage {
    private Integer totalCount;
    private Integer firstShownPageNumber;
    private Integer lastShownPageNumber;
    private Integer lastPageNumber;
    private Integer currentPageNumber;
    private List<Phone> phoneList;
    private List<Phone> curentPagePhoneList;
    public static final int AMOUNT_OF_PHONES_ON_PAGE = 10;
    public static final int AMOUNT_OF_ACCESSIBLE_PAGES_ON_LINE = 5;


    public CurrentPhonePage(Integer currentPageNumber, List<Phone> phoneList) {
        this.totalCount = phoneList.size();
        this.currentPageNumber = currentPageNumber;
        this.phoneList = phoneList;
        this.firstShownPageNumber = currentPageNumber - AMOUNT_OF_ACCESSIBLE_PAGES_ON_LINE / 2;
        if(firstShownPageNumber <= 0)
            firstShownPageNumber = 1;
        this.lastPageNumber = (int)Math.ceil(totalCount / AMOUNT_OF_PHONES_ON_PAGE);
        this.lastShownPageNumber = firstShownPageNumber == 1 ?
        AMOUNT_OF_ACCESSIBLE_PAGES_ON_LINE : currentPageNumber + AMOUNT_OF_ACCESSIBLE_PAGES_ON_LINE / 2;
        if(lastShownPageNumber > lastPageNumber)
            lastShownPageNumber = lastPageNumber;
        this.curentPagePhoneList = getPhoneSublistByCurrentIndex();
    }

    private List<Phone> getPhoneSublistByCurrentIndex() {
        int startIndex = AMOUNT_OF_PHONES_ON_PAGE * (currentPageNumber - 1);
        int endIndex = currentPageNumber * AMOUNT_OF_PHONES_ON_PAGE - 1;
        if(endIndex < phoneList.size())
            return phoneList.subList(startIndex, endIndex);
        else if(phoneList.size() == 0)
            return phoneList;
        return phoneList.subList(startIndex, phoneList.size() - 1);
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
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

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public List<Phone> getCurentPagePhoneList() {
        return curentPagePhoneList;
    }

    public void setCurentPagePhoneList(List<Phone> curentPagePhoneList) {
        this.curentPagePhoneList = curentPagePhoneList;
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
