package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {

    @Resource
    private PhoneDao phoneDao;

    private static int AMOUNT_PHONES_ON_PAGE = 10;

    private static final int FIRST_PAGE_NUMBER = 1;

    private static final int AROUND_PAGES_COUNT = 3;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(defaultValue = "brand") PhoneDao.OrderBy order,
                                  @RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                  @RequestParam(required = false) String query, Model model)
                                  throws UnsupportedEncodingException {

        int phonesCount = getPhoneCount(query);
        int pagesCount = getPagesCount(phonesCount);
        pageNumber = normalizePageNumber(pageNumber, phonesCount);

        int offset = ((pageNumber-1)*AMOUNT_PHONES_ON_PAGE);
        int limit = AMOUNT_PHONES_ON_PAGE;

        List<Phone> phones = getPhoneList(query, order, offset, limit);

        model.addAttribute("phonesCount", phonesCount);
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageCount", pagesCount);
        model.addAttribute("startPaginationNumber",Math.max(pageNumber - AROUND_PAGES_COUNT,FIRST_PAGE_NUMBER));
        model.addAttribute("finishPaginationNumber",Math.min(pageNumber + AROUND_PAGES_COUNT,pagesCount));
        model.addAttribute("phones", phones);
        model.addAttribute("order", order.name());
        if(query != null) {
            model.addAttribute("query", encodeValue(query));
        }
        return "productList";
    }


    private int getPhoneCount(String query){
        if(query == null){
            return phoneDao.phonesCount();
        }
        return phoneDao.phonesCountByQuery(query);
    }

    private List<Phone> getPhoneList(String query, PhoneDao.OrderBy order, int offset, int limit){
        if(query == null){
            return phoneDao.findAllInOrder(order, offset, limit);
        }
        return phoneDao.getPhonesByQuery(query, order, offset, limit);
    }

    private int normalizePageNumber(int pageNumber, int phonesCount){
        pageNumber = Math.max(pageNumber,FIRST_PAGE_NUMBER);
        int lastPageNumber = getPagesCount(phonesCount);
        pageNumber = Math.min(pageNumber,lastPageNumber);
        return pageNumber;
    }

    private int getPagesCount(int phonesCount){
        if (phonesCount == 0){
            return 1;
        }
        return (int)Math.ceil((double)phonesCount/AMOUNT_PHONES_ON_PAGE);
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
