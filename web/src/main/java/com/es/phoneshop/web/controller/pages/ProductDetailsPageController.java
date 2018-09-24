package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.model.phone.Phone;
import com.es.phoneshop.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.exceptions.PhoneNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "productDetails/{id}" )
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
         throw new PhoneNotFoundException();
        }
    }

}
