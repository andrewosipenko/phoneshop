package com.es.phoneshop.web.constant;

public interface ControllerMapping {

    String AJAX_CART_CONTROLLER = "/ajaxCart";

    String PRODUCT_LIST_PAGE = "/productList";

    String PRODUCT_DELAILS_PAGE = "/productDetails/{id}";

    String CART_PAGE = "/cart";

    String ORDER_PAGE = "/order";

    String ORDER_OVERVIEW_PAGE = "/orderOverview/{id}";

    String LOGIN_PAGE = "/login";

    interface AdminPanel {
        String ORDERS_PAGE = "/admin/orders";

        String ORDER_DETAILS_PAGE = "/{id}";
    }

}
