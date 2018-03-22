package com.es.phoneshop.web.bean;

public class Pagination {

    private int pageNumber;

    private int startPaginationNumber;

    private int finishPaginationNumber;

    public Pagination() {
    }

    public Pagination(int pageNumber, int startPaginationNumber, int finishPaginationNumber) {
        this.pageNumber = pageNumber;
        this.startPaginationNumber = startPaginationNumber;
        this.finishPaginationNumber = finishPaginationNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getStartPaginationNumber() {
        return startPaginationNumber;
    }

    public void setStartPaginationNumber(int startPaginationNumber) {
        this.startPaginationNumber = startPaginationNumber;
    }

    public int getFinishPaginationNumber() {
        return finishPaginationNumber;
    }

    public void setFinishPaginationNumber(int finishPaginationNumber) {
        this.finishPaginationNumber = finishPaginationNumber;
    }
}
