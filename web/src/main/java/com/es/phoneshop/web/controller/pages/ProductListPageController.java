package com.es.phoneshop.web.controller.pages;

import com.es.core.model.DAO.phone.PhoneDao;
import com.es.core.model.DAO.phone.consts.SortField;
import com.es.core.model.DAO.phone.consts.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    private static final int DEFAULT_RECORDS_LIMIT = 10;

    @Autowired
    private PhoneDao phoneDao;

    @GetMapping
    public String showProductList(@RequestParam(required = false, defaultValue = "brand") String sort,
                                  @RequestParam(required = false, defaultValue = "ASC") String order,
                                  @RequestParam(required = false, defaultValue = " ") String query,
                                  @RequestParam(required = false, defaultValue = "1", value = "page") String currentPage,
                                  Model model) {

        int numberOfPages = getNumberOfPages(phoneDao.getRecordsQuantity(query), DEFAULT_RECORDS_LIMIT);
        int offset = calculateOffset(Integer.parseInt(currentPage), DEFAULT_RECORDS_LIMIT, numberOfPages);

        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("phones", phoneDao.findAll(SortField.valueOf(sort), SortOrder.valueOf(order), query, offset, DEFAULT_RECORDS_LIMIT));
        return "productList";
    }

    private int calculateOffset(int currentPage, int numberOfRecordsPerPage, int numberOfPages) {
        if (currentPage <= 0) {
            return 0;
        }
        if (currentPage > numberOfPages) {
            return (numberOfPages - 1) * numberOfRecordsPerPage;
        }
        return (currentPage - 1) * numberOfRecordsPerPage;
    }

    private int getNumberOfPages(int totalRecordsQuantity, int recordsLimit) {
        if (totalRecordsQuantity % recordsLimit == 0) {
            return totalRecordsQuantity / recordsLimit;
        } else
            return totalRecordsQuantity / recordsLimit + 1;
    }
}
