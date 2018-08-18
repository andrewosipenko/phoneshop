package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.Phone;

@Controller
@RequestMapping(value = "/productDetails/{id}" )
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductDetails(@PathVariable("id") long id, Model model){
        Optional<Phone> phone = phoneDao.get(id);

        if(phone.isPresent()) {
            model.addAttribute("phone", phone.get());
            model.addAttribute("colors", phoneDao.getPhoneColors(id));
            return "productDetails";
        }
        else{
            model.addAttribute("id", id);
            return "404";
        }
    }

}
