package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.services.PaginationService;
import com.es.phoneshop.web.services.PhoneWebService;
import org.springframework.security.core.Authentication;
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
    private final String ATTRIBUTE_LOGIN = "login";
    private final String ATTRIBUTE_PAGE_START_NUMBER = "pageStartNumber";
    private final String ATTRIBUTE_PAGE = "page";
    private final String ATTRIBUTE_PAGE_TO_DISPLAY = "pagesToDisplay";
    private final String ATTRIBUTE_SORT = "sort";
    private final String ATTRIBUTE_DIRECTION = "direction";
    private final String ATTRIBUTE_SEARCH_TEXT = "searchText";
    private final String ATTRIBUTE_PHONES = "phones";
    private final String PAGE_PRODUCT_LIST = "productList";
    private final String REDIRECT_PAGE_PRODUCT_LIST = "redirect:/productList?page=";

    @Resource
    private PhoneWebService phoneWebService;
    @Resource
    private PaginationService paginationService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(name = "sort", required = false, defaultValue = "brand") String sort,
                                  @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
                                  @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                  Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        Integer pageStartNumber = paginationService.getPageStartNumber(page, search);
        model.addAttribute(ATTRIBUTE_PAGE_START_NUMBER, pageStartNumber);
        page = paginationService.getAvailableNewPage(page, search);
        model.addAttribute(ATTRIBUTE_PAGE, page);
        Integer pagesToDisplay = paginationService.getAmountPagesToDisplay(pageStartNumber, search);
        model.addAttribute(ATTRIBUTE_PAGE_TO_DISPLAY, pagesToDisplay);
        model.addAttribute(ATTRIBUTE_SORT, sort);
        model.addAttribute(ATTRIBUTE_DIRECTION, direction);
        model.addAttribute(ATTRIBUTE_SEARCH_TEXT, search);
        int offset = PaginationService.PHONES_TO_DISPLAY * (page - 1);
        List<Phone> phones = phoneWebService.getPhoneList(offset, sort, direction, search);
        model.addAttribute(ATTRIBUTE_PHONES, phones);
        return PAGE_PRODUCT_LIST;
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
        return REDIRECT_PAGE_PRODUCT_LIST + newPage + otherParams;
    }
}
