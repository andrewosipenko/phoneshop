package com.es.core.model;

public class PaginationModel {

    private int phonesOnPage;

    private int countPages;

    public PaginationModel() {
        this.phonesOnPage = 10;
    }

    public int getPhonesOnPage() {
        return phonesOnPage;
    }

    public int getCountPages() {
        return countPages;
    }

    public void setPhonesOnPage(int phonesOnPage) {
        this.phonesOnPage = phonesOnPage;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

}
