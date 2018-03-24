package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.phoneshop.web.controller.throwable.NoSuchPageFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;
    private static final int PHONES_PER_PAGE = 10;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        int total = phoneDao.count();
        int pagesTotal = total / PHONES_PER_PAGE + 1;
        if (page > pagesTotal || page <= 0)
            throw new NoSuchPageFoundException();
        int firstIndex = (page - 1) * PHONES_PER_PAGE;
        List<Phone> phoneList = phoneDao.findAll(firstIndex, PHONES_PER_PAGE);
        model.addAttribute("currentPage", page);
        model.addAttribute("pagesTotal", pagesTotal);
        model.addAttribute("phones", phoneList);
        return "productList";
    }
}
