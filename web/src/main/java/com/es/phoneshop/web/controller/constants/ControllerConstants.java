package com.es.phoneshop.web.controller.constants;

public interface ControllerConstants {
    interface ProductListConstants {
        String CURRENT_PAGE_NUMBER = "page";
        String ORDER_BY_ATTRIBUTE = "order";
        String SEARCH_QUERY_STRING = "query";
        String PRODUCT_PAGE = "productPage";
        String CART_ITEM = "cartItem";
    }
    interface ProductDetailsConstants {
        String CART_ITEM = "cartItem";
        String PHONE = "phone";
    }
    interface CartPageConstants {
        String CART_PAGE_INFO = "cartPageInfo";
        String CART = "cart";
        String PHONE_ID = "phoneId";
    }

    interface OrderPageConstants {
        String CART = "cart";
        String REJECTED_PHONES = "rejectedPhones";
        String ORDER = "order";
        String TOTAL = "total";
        String ORDER_FORM = "orderForm";
        String OUT_OF_STOCK_ATTRIBUTE = "outOfStock";
        String OUT_OF_STOCK_MESSAGE = "Some products are out of stock so that they were removed from your cart";
    }
}
