package com.es.phoneshop.web.bean;

import com.es.core.model.order.Order;

import java.util.List;

public class OrdersPage {
    private long count;

    private List<Order> orderList;

    private Pagination pagination;

    public OrdersPage() {
    }

    public OrdersPage(long count, List<Order> orderList, Pagination pagination) {
        this.count = count;
        this.orderList = orderList;
        this.pagination = pagination;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
