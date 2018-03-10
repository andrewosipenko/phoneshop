package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model) {
        model.addAttribute("phones", phoneDao.findAll(0, 10));
        System.out.println("This is done in order to check if the implementation of JdbcPhoneDao truly works : \n" +
                "Will be removed in the the next commit ");
        Phone phone = phoneDao.get(1000L).get();
        System.out.println(phone.getBrand() + " works "  + phone.getId());
        phone.setDescription("JJJJ");
        phoneDao.save(phone);
        phone.setId(2000L);
        phone.setBrand("KLK");
        phone.setModel("KKK");
        phoneDao.save(phone);
        System.out.println(phoneDao.get(1000L).get().getDescription() + " works "  + phone.getId());
        System.out.println(phoneDao.get(2000L).get().getBrand() + " works "  + phone.getId());
        return "productList";
    }
}
