package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.exceptions.InvalidUrlException;
import com.es.phoneshop.web.services.PaginationService;
import com.es.phoneshop.web.services.PhoneWebService;
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
    private PhoneWebService phoneWebService;

    @Resource
    private PaginationService paginationService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(name = "sort", required = false, defaultValue = "brand") String sort,
                                  @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
                                  @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                  Model model) throws InvalidUrlException {

        Integer pageStartNumber = paginationService.getPageStartNumber(page, search);
        model.addAttribute("pageStartNumber", pageStartNumber);
        page = paginationService.getAvailableNewPage(page, search);
        model.addAttribute("page", page);
        Integer pagesToDisplay = paginationService.getAmountPagesToDisplay(pageStartNumber, search);
        model.addAttribute("pagesToDisplay", pagesToDisplay);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("searchText", search);
        int offset = PaginationService.PHONES_TO_DISPLAY * (page - 1);
        List<Phone> phones = phoneWebService.getPhoneList(offset, sort, direction, search);
        model.addAttribute("phones", phones);
        return "productList";
    }
}
