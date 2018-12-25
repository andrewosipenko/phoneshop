package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.services.PaginationService;
import com.es.phoneshop.web.services.PhoneWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
                                  Model model) {

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

    @RequestMapping(method = RequestMethod.GET, params = {"page", "action", "search"})
    public String changePage(@RequestParam  Map<String, String> params){
        Integer currentPage = Integer.parseInt(params.get("page"));
        String currentAction = params.get("action");
        String currentSearch = params.get("search");
        String otherParams = new String();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!entry.getKey().equals("page") && !entry.getKey().equals("action")){
                otherParams = otherParams.concat("&").concat(entry.getKey()).concat("=").concat(entry.getValue());
            }
        }
        Integer newPage = paginationService.getNewPage(currentPage, currentAction, currentSearch);
        return "redirect:/productList?page=" + newPage + otherParams;
    }
}
