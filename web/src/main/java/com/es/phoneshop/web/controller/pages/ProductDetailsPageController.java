package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.ControllerConstants;
import com.es.phoneshop.web.exception.PageNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    private final static String PHONE_ATTRIBUTE = "phone";

    @Resource
    private PhoneDao phoneDao;

    @GetMapping(path = "/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) throws PageNotFoundException {
        Phone phone = phoneDao.get(id).orElseThrow(PageNotFoundException::new);
        model.addAttribute(PHONE_ATTRIBUTE, phone);
        return ControllerConstants.PRODUCT_DETAILS_PAGE_NAME;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public void handleNumberFormatException(InvalidFormatException e) throws PageNotFoundException {
        throw new PageNotFoundException(e);
    }

}
