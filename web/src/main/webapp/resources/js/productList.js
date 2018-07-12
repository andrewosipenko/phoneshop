"use strict";

var PAGE_URL = "/phoneshop-web/productList";


$("#pagination").each(function () {
    var limit = $(this).data("limit") !== "" ? $(this).data("limit") : 10;
    var amount = $(this).data("amount");
    var page = $(this).data("page");
    $(this).twbsPagination({
            initiateStartPageClick: false,
            totalPages: Math.ceil(amount/limit),
            startPage: page,
            visiblePages: 5,
            onPageClick: function(event, page){
                var link = PAGE_URL
                    +"?page=" + page
                    + ($(this).data("limit")!=="" ? "&limit="+$(this).data("limit") : "")
                    + ($(this).data("sortby")!=="" ? "&sortBy="+$(this).data("sortby") : "")
                    + ($(this).data("search")!=="" ? "&search="+$(this).data("search") : "");
                window.location.replace(link);
            }
    });
});


