package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.web.controller.form.AddToCartForm;
import com.es.phoneshop.web.controller.service.PhonePageService;
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
    private PhonePageService phonePageService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model, @RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "brand") SortBy sortBy,
                                  @RequestParam(required = false) String search) {
        model.addAttribute("currentPage", page);
        model.addAttribute("pagesTotal", phonePageService.countPagesTotal(search));
        model.addAttribute("phones", phonePageService.getPhoneList(search, sortBy, page));
        model.addAttribute("addToCartForm", new AddToCartForm());
        return "productList";
    }
}
