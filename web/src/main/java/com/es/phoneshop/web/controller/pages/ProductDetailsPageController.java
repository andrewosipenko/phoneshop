package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.phone.exception.NoSuchPhoneException;
import com.es.phoneshop.web.controller.service.phone.PhoneWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;

    @GetMapping(value = "/phoneId={phoneId}")
    public String showProductDetails(@PathVariable Long phoneId, Model model){
        model.addAttribute("phone", phoneDao.get(phoneId).orElseThrow(NoSuchPhoneException::new));
        return "productDetails";
    }
}
