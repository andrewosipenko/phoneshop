package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.phone.dao.PhoneDao;
import com.es.phoneshop.core.phone.dao.PhoneDaoSelector;
import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.core.phone.model.Phone;
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
