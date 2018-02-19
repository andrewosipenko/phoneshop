package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.ControllerConstants;
import com.es.phoneshop.web.exception.PageNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String showProductDetails(@PathVariable Long id, Model model) throws PageNotFoundException {
        Phone phone = phoneDao.get(id).orElseThrow(PageNotFoundException::new);
        model.addAttribute(ControllerConstants.PHONE_ATTRIBUTE, phone);
        return ControllerConstants.PRODUCT_DETAILS_PAGE_NAME;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public void handleNumberFormatException(InvalidFormatException e) throws PageNotFoundException {
        throw new PageNotFoundException(e);
    }

}
