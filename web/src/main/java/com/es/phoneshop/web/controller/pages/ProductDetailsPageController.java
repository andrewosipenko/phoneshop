package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.exception.PageNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static com.es.phoneshop.web.constant.ControllerConstants.PRODUCT_DETAILS_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.PRODUCT_DELAILS_PAGE;

@Controller
@RequestMapping(PRODUCT_DELAILS_PAGE)
public class ProductDetailsPageController {

    private final static String PHONE_ATTRIBUTE = "phone";

    @Resource
    private PhoneDao phoneDao;

    @GetMapping
    public String showProductDetails(@PathVariable Long id, Model model) throws PageNotFoundException {
        Phone phone = phoneDao.get(id).orElseThrow(PageNotFoundException::new);
        model.addAttribute(PHONE_ATTRIBUTE, phone);
        return PRODUCT_DETAILS_PAGE_NAME;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public void handleNumberFormatException(InvalidFormatException e) throws PageNotFoundException {
        throw new PageNotFoundException(e);
    }

}
