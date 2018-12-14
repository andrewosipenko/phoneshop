package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(name = "sort", required = false, defaultValue = "brand") String sortBy,
                                  @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction,
                                  @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                  Model model) {

        model.addAttribute("pageStartNumber", 1);
        model.addAttribute("pagesToDisplay", 10);

        model.addAttribute("phones", phoneDao.findAll(0, 10));

        return "productList";
    }
}
