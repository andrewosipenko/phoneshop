package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.dao.util.PhoneDaoSelector;
import com.es.phoneshop.web.controller.throwable.NoSuchPageFoundException;
import com.es.core.model.phone.dao.util.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private ConversionService conversionService;
    private static final int PHONES_PER_PAGE = 10;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setConversionService(conversionService);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model, @RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "brand") SortBy sortBy, @RequestParam(required = false) String search) {
        PhoneDaoSelector selector = new PhoneDaoSelector().searching(search);

        int total = phoneDao.count(selector);
        int pagesTotal = total / PHONES_PER_PAGE + 1;
        if (page > pagesTotal || page <= 0)
            throw new NoSuchPageFoundException();
        int begin = (page - 1) * PHONES_PER_PAGE;

        selector = selector.sortedBy(sortBy)
                .offset(begin)
                .limit(PHONES_PER_PAGE);

        List<Phone> phoneList = phoneDao.findAll(selector);
        model.addAttribute("currentPage", page);
        model.addAttribute("pagesTotal", pagesTotal);
        model.addAttribute("phones", phoneList);
        return "productList";
    }
}
